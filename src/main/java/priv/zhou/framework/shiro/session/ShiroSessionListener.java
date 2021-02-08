package priv.zhou.framework.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * session 监听
 */
public class ShiroSessionListener implements SessionListener {

    /**
     * 统计在线人数
     */
    private final AtomicInteger sessionCount = new AtomicInteger(0);


    @Override
    public void onStart(Session session) {
        // 会话创建，在线人数加一
        sessionCount.incrementAndGet();
    }


    @Override
    public void onStop(Session session) {
        // 会话退出，在线人数减一
        sessionCount.decrementAndGet();
    }


    @Override
    public void onExpiration(Session session) {
        // 会话过期，在线人数减一
        sessionCount.decrementAndGet();
    }


    /**
     * 获取在线人数
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }
}
