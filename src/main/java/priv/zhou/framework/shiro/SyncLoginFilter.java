package priv.zhou.framework.shiro;

import com.google.common.collect.Lists;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
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

import static priv.zhou.common.constant.GlobalConst.STR_0;

/**
 * 登陆限制过滤器
 */
@Slf4j
@Getter
@Setter
@Accessors(chain = true)
public class SyncLoginFilter extends AccessControlFilter {

    private final String name = "loginLimit";

    /**
     * session踢出标识
     */
    private String kickOutKey = "KICK_OUT";


    /**
     * 踢出后到的地址
     */
    private String outUrl;

    /**
     * 同一账号最多同时登录数
     */
    private int maxSync = 1;

    private SessionDAO sessionDAO;

    private SessionManager sessionManager;


    private Cache<String, Deque<Serializable>> cache;


    public SyncLoginFilter setCacheManager(Cache<String, Deque<Serializable>> cache) {
        this.cache = cache;
        return this;
    }

    public void remove(String username) {
        cache.get(username).remove();
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
        if (null == session.getAttribute(kickOutKey) && !deque.contains(session.getId())) {
            syncCache = true;
            UserAgent userAgent = UserAgent.parseUserAgentString(((ShiroHttpServletRequest) request).getHeader("User-Agent"));
            session.setUsername(userPrincipal.getUsername());
            session.setRoleNames(userPrincipal.getRoleNames());
            session.setBrowser(userAgent.getBrowser().getName());
            session.setOs(userAgent.getOperatingSystem().getName());
            deque.push(session.getId());
        }

        // 4.登陆队列大于限制,标识踢出session
        while (deque.size() > maxSync) {
            Serializable sessionId = deque.removeLast();
            try {
                Session kickOutSession = sessionManager.getSession(new DefaultSessionKey(sessionId));
                if (kickOutSession != null) {
                    syncCache = true;
                    kickOutSession.setAttribute(kickOutKey, STR_0);
                }
            } catch (UnknownSessionException e) {
                log.info(e.getMessage());
            }
        }

        // 5.更新缓存
        if(syncCache){
            cache.put(userPrincipal.getUsername(), deque);
        }

        // 5.未被踢出的session
        if (null == session.getAttribute(kickOutKey)) {
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