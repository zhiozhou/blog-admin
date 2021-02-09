package priv.zhou.framework.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.crazycake.shiro.SessionInMemory;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou
 * @date 2021.02.08
 * @description 参考 shiro-redis 开源项目 Git地址 https://github.com/alexxiyang/shiro-redis
 */
@Slf4j
public class ShiroSessionDAO extends EnterpriseCacheSessionDAO {

    private String keyPrefix;

    /**
     * doReadSession在登录时会被调用10次左右,将会话保存到ThreadLocal以解决此问题
     */
    private final static ThreadLocal<Map<Serializable, SessionInMemory>> sessionsInThread = new ThreadLocal<>();

    /**
     * session在ThreadLocal中缓存的毫秒数
     */
    private long sessionThreadExpire = 1000L;


    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session instanceof ShiroSession) {
            ShiroSession shiroSession = (ShiroSession) session;
            if (!shiroSession.isChanged()) {
                return;
            }
            // 常置false，只有当lastAccessTime以为的字段改变时才会变为true
            shiroSession.setChanged(false);
        }
        super.update(session);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (null == sessionId) {
            return null;
        }
        Session session = getSessionFromThreadLocal(sessionId);
        if (null != session) {
            return session;
        }
        session = super.doReadSession(sessionId);
        setSessionToThreadLocal(sessionId, session);
        return session;
    }


    private void setSessionToThreadLocal(Serializable sessionId, Session session) {
        Map<Serializable, SessionInMemory> sessionMap = sessionsInThread.get();
        if (null == sessionMap) {
            sessionMap = new HashMap<>();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(session);
        sessionMap.put(sessionId, sessionInMemory);
    }


    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Map<Serializable, SessionInMemory> sessionMap = sessionsInThread.get();
        if (null == sessionMap) {
            return null;
        }
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (null == sessionInMemory) {
            return null;
        }
        Session session = sessionInMemory.getSession();
        if (System.currentTimeMillis() - sessionInMemory.getCreateTime().getTime() >= sessionThreadExpire) {
            sessionMap.remove(sessionId);
        }
        return session;
    }

    private String getSessionKey(Serializable sessionId) {
        return this.keyPrefix + sessionId;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public void setSessionThreadExpire(long sessionThreadExpire) {
        this.sessionThreadExpire = sessionThreadExpire;
    }

}
