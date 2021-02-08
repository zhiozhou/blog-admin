package priv.zhou.framework.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.crazycake.shiro.SessionInMemory;
import priv.zhou.common.tools.RedisUtil;

import java.io.Serializable;
import java.util.*;

/**
 * @author: wangsaichao
 * @date: 2018/6/22
 * @description: 参考 shiro-redis 开源项目 Git地址 https://github.com/alexxiyang/shiro-redis
 */
@Slf4j
public class RedisSessionDAO extends AbstractSessionDAO {

    private String keyPrefix = "shiro:session:";


    /**
     * doReadSession be called about 10 times when login.
     * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
     * The default value is 1000 milliseconds (1s).
     * Most of time, you don't need to change it.
     */
    private long sessionInMemoryTimeout = 1000L;

    /**
     * expire time in seconds
     */
    private static final int DEFAULT_EXPIRE = -2;
    private static final int NO_EXPIRE = -1;

    /**
     * Please make sure expire is longer than sesion.getTimeout()
     */
    private int expire = DEFAULT_EXPIRE;

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private static ThreadLocal sessionsInThread = new ThreadLocal();

    @Override
    public void update(Session session) throws UnknownSessionException {
        //如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }

            if (session instanceof ShiroSession) {
                // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变
                ShiroSession ss = (ShiroSession) session;
                if (!ss.isChanged()) {
                    return;
                }
                //如果没有返回 证明有调用 setAttribute往redis 放 的时候永远设置为false
                ss.setChanged(false);
            }

            this.saveSession(session);
        } catch (Exception e) {
            log.warn("update Session is failed", e);
        }
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        String key = getRedisSessionKey(session.getId());
        if (expire == DEFAULT_EXPIRE) {
            RedisUtil.set(key, session, (int) (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
            return;
        }
        if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
            log.warn("Redis session expire time: "
                    + (expire * MILLISECONDS_IN_A_SECOND)
                    + " is less than Session timeout: "
                    + session.getTimeout()
                    + " . It may cause some problems.");
        }
        RedisUtil.set(key, session, expire);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            log.error("session or session id is null");
            return;
        }
        try {
            RedisUtil.delete(getRedisSessionKey(session.getId()));
        } catch (Exception e) {
            log.error("delete session error. session id= {}", session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        try {
            Set<String> keys = RedisUtil.keys(this.keyPrefix + "*");
            if (keys != null && keys.size() > 0) {
                for (String key : keys) {
                    Session s = (Session) RedisUtil.get(key);
                    sessions.add(s);
                }
            }
        } catch (Exception e) {
            log.error("get active sessions error.");
        }
        return sessions;
    }

    public int getActiveSessionsSize() {
        try {
            return RedisUtil.keys(this.keyPrefix + "*").size();
        } catch (Exception e) {
            log.error("get active sessions error.");
        }
        return 0;
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (session == null) {
            log.error("session is null");
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            log.warn("session id is null");
            return null;
        }
        Session s = getSessionFromThreadLocal(sessionId);

        if (s != null) {
            return s;
        }

        log.debug("read session from redis");
        try {
            s = (Session) RedisUtil.get(getRedisSessionKey(sessionId));
            setSessionToThreadLocal(sessionId, s);
        } catch (Exception e) {
            log.error("read session error. settionId= {}", sessionId);
        }
        return s;
    }

    private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<Serializable, SessionInMemory>();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(s);
        sessionMap.put(sessionId, sessionInMemory);
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;

        if (sessionsInThread.get() == null) {
            return null;
        }

        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        Date now = new Date();
        long duration = now.getTime() - sessionInMemory.getCreateTime().getTime();
        if (duration < sessionInMemoryTimeout) {
            s = sessionInMemory.getSession();
            log.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }

        return s;
    }

    private String getRedisSessionKey(Serializable sessionId) {
        return this.keyPrefix + sessionId;
    }


    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public long getSessionInMemoryTimeout() {
        return sessionInMemoryTimeout;
    }

    public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}
