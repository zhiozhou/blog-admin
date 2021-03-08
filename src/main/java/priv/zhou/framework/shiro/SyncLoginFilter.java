package priv.zhou.framework.shiro;

import com.google.common.collect.Lists;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.shiro.session.ShiroSession;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;
import java.util.concurrent.TimeUnit;

/**
 * 登陆限制过滤器
 */
@Slf4j
@Getter
@Setter
public class SyncLoginFilter extends AccessControlFilter {

    private final String name = "loginLimit";

    /**
     * 踢出后到的地址
     */
    private String outUrl;

    /**
     * 同一账号最多同时登录数
     */
    private int maxSync = 1;

    /**
     * 被踢出后的等待时间，5分钟
     */
    private Long kickedWait = TimeUnit.MINUTES.toMillis(5);

    private SessionDAO sessionDAO;

    private Cache<String, Deque<Serializable>> cache;

    public void remove(String username, Serializable sessionId) {
        if (null == sessionId) {
            return;
        }
        Deque<Serializable> deque = cache.get(username);
        if (null != deque && deque.removeIf(sessionId::equals)) {
            cache.put(username, deque);
        }
    }

    /**
     * 返回true继续拦截器链执行
     * 返回false表示自己处理完（比如重定向到另一个页面）
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 1.用户没有登陆 不进行拦截
        Subject subject = ShiroUtil.getSubject();
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }

        // 2.获取账号登陆队列
        boolean syncCache = false;
        ShiroSession session = (ShiroSession) sessionDAO.readSession(subject.getSession().getId());
        UserPrincipal userPrincipal = (UserPrincipal) subject.getPrincipal();
        Deque<Serializable> deque = cache.get(userPrincipal.getUsername());
        if (null == deque) {
            deque = Lists.newLinkedList();
        }

        // 3.放入登陆队列
        if (session.getState().equals(0) && !deque.contains(session.getId())) {
            syncCache = true;
            UserAgent userAgent = UserAgent.parseUserAgentString(((ShiroHttpServletRequest) request).getHeader("User-Agent"));
            session.setUsername(userPrincipal.getUsername());
            session.setRoleNames(userPrincipal.getRoleNames());
            session.setBrowser(userAgent.getBrowser().getName());
            session.setOs(userAgent.getOperatingSystem().getName());
            sessionDAO.update(session);
            deque.push(session.getId());
        }

        // 4.登陆队列大于限制,标识踢出session
        while (deque.size() > maxSync) {
            Serializable sessionId = deque.removeLast();
            try {
                ShiroSession kickOutSession = (ShiroSession) sessionDAO.readSession(sessionId);
                if (kickOutSession != null) {
                    syncCache = true;
                    kickOutSession.setState(11);
                    kickOutSession.setTimeout(kickedWait);
                    System.out.println("kickout id -->" + sessionId);
                    sessionDAO.update(kickOutSession);
                }
            } catch (UnknownSessionException e) {
                log.info(e.getMessage());
            }
        }

        // 5.更新缓存
        if (syncCache) {
            cache.put(userPrincipal.getUsername(), deque);
        }

        if (session.getState().equals(0)) {
            // 正常状态
            return true;
        } else if (session.getState().equals(11)) {

            // 被踢出状态
            subject.logout();
            WebUtils.issueRedirect(request, response, outUrl);
        }

        return false;
    }

    /**
     * 是否允许访问，返回true表示允许
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }
}