package priv.zhou.framework.shiro;

import com.google.common.collect.Lists;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import priv.zhou.module.system.user.domain.dto.UserDTO;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Deque;

import static priv.zhou.common.constant.GlobalConst.STR_0;
import static priv.zhou.common.constant.ShiroConst.LOGIN_LIMIT_CACHE_NAME;
import static priv.zhou.common.constant.ShiroConst.SESSION_KICK_OUT_KEY;

/**
 * 登陆限制过滤器
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class LoginLimitFilter extends AccessControlFilter {

    private final String name = "loginLimit";

    /**
     * 踢出后到的地址
     */
    private String outUrl;

    /**
     * 同一个帐号最大会话数 默认1
     */
    private int limit = 1;


    private SessionManager sessionManager;


    private Cache<String, Deque<Serializable>> cache;


    public LoginLimitFilter setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache(LOGIN_LIMIT_CACHE_NAME);
        return this;
    }

    public void remove(Session session) {
        String username = (String) session.getAttribute("username");
        Deque<Serializable> deque = cache.get(username);
        deque.remove();
    }

    /**
     * 返回true继续拦截器链执行
     * 返回false表示自己处理完（比如重定向到另一个页面）
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // 1.用户没有登陆 不进行拦截
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated() && !subject.isRemembered()) {
            return true;
        }


        // 2.获取账号登陆队列
        Session session = subject.getSession();
        UserDTO userDTO = (UserDTO) subject.getPrincipal();
        Deque<Serializable> deque = cache.get(userDTO.getUsername());
        if (null ==deque) {
            cache.put(userDTO.getUsername(), deque = Lists.newLinkedList());
        }

        // 3.放入登陆队列
        if (null == session.getAttribute(SESSION_KICK_OUT_KEY) && !deque.contains(session.getId())) {

            UserAgent userAgent = UserAgent.parseUserAgentString(((ShiroHttpServletRequest) request).getHeader("User-Agent"));
            session.setAttribute("username", userDTO.getUsername());
            session.setAttribute("role", userDTO.getRole());
            session.setAttribute("browser", userAgent.getBrowser().getName());
            session.setAttribute("os", userAgent.getOperatingSystem().getName());
            deque.push(session.getId());
        }

        // 4.登陆队列大于限制,标识踢出session
        while (deque.size() > limit) {
            Serializable sessionId = deque.removeLast();
            try {
                Session kickOutSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
                if (kickOutSession != null) {
                    kickOutSession.setAttribute(SESSION_KICK_OUT_KEY, STR_0);
                }
            } catch (UnknownSessionException e) {
                log.info(e.getMessage());
            }
        }

        // 5.当前没被标识
        if (null == session.getAttribute(SESSION_KICK_OUT_KEY)) {
            return true;
        }

        // 6.踢出session
        subject.logout();
        WebUtils.issueRedirect(request, response, outUrl);
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