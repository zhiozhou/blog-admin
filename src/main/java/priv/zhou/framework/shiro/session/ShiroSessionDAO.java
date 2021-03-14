package priv.zhou.framework.shiro.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.crazycake.shiro.SessionInMemory;
import org.springframework.context.ApplicationListener;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import priv.zhou.common.tools.DateUtil;
import priv.zhou.common.tools.ShiroUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou
 * @description 参考 shiro-redis 开源项目 Git地址 https://github.com/alexxiyang/shiro-redis
 * @since 2021.02.08
 */
@Slf4j
public class ShiroSessionDAO extends EnterpriseCacheSessionDAO implements ApplicationListener<ServletRequestHandledEvent> {

    /**
     * session访问时间更新间隔
     */
    private final long accessTimeUpdateSeptum;

    /**
     * readSession在登录时会被调用10次左右,将会话保存到ThreadLocal以解决此问题
     */
    private final static ThreadLocal<Map<Serializable, SessionInMemory>> sessionLocals = new ThreadLocal<>();

    public ShiroSessionDAO(long accessTimeUpdateSeptum) {
        this.accessTimeUpdateSeptum = accessTimeUpdateSeptum;
    }


    @Override
    public void delete(Session session) {
        System.out.println(Thread.currentThread().getId()+" removeSession "+session.getId());
        Map<Serializable, SessionInMemory> sessionMap = sessionLocals.get();
        if (null != sessionMap) {
            sessionMap.remove(session.getId());
        }
        super.delete(session);
    }



    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session instanceof ShiroSession) {
            ShiroSession shiroSession = (ShiroSession) session;
            if (null != shiroSession.getLastUpdatedTime() && shiroSession.isSeptumUpdate() &&
                    shiroSession.getLastAccessTime().getTime() - shiroSession.getLastUpdatedTime().getTime() < accessTimeUpdateSeptum) {
                // 间隔内已更新过
                return;
            }
            shiroSession.setSeptumUpdate(true);
            shiroSession.setLastUpdatedTime(DateUtil.now());
        }
        setThreadLocal(session.getId(), session);
        super.update(session);
    }

    @Override
    public Session readSession(Serializable sessionId) {
        System.out.println(Thread.currentThread().getId()+" readSession "+sessionId);
        if (null == sessionId) {
            return null;
        }
        Session session = getLocalSession(sessionId);
        if (null != session) {
            return session;
        }
        try {
            session = super.readSession(sessionId);
            setThreadLocal(sessionId, session);
            return session;
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        Map<Serializable, SessionInMemory> sessionMap = sessionLocals.get();
        if (null != sessionMap) {
            sessionLocals.remove();
        }
    }


    private void setThreadLocal(Serializable sessionId, Session session) {
        if (null == sessionId || null == session) {
            return;
        }
        Map<Serializable, SessionInMemory> sessionMap = sessionLocals.get();
        if (null == sessionMap) {
            sessionMap = new HashMap<>();
            sessionLocals.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(session);
        sessionMap.put(sessionId, sessionInMemory);
    }


    private Session getLocalSession(Serializable sessionId) {
        Map<Serializable, SessionInMemory> sessionMap = sessionLocals.get();
        if (null == sessionMap) {
            return null;
        }
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (null == sessionInMemory) {
            return null;
        }
        return sessionInMemory.getSession();
    }


}
