package priv.zhou.framework.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.collect.Maps;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.zhou.common.tools.AesUtil;
import priv.zhou.framework.shiro.LoginLimitFilter;
import priv.zhou.framework.shiro.RetryLimitMatcher;
import priv.zhou.framework.shiro.UserRealm;
import priv.zhou.framework.shiro.UserSessionListener;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static priv.zhou.common.param.CONSTANT.*;

@Configuration
public class ShiroConfigurer {


    /**
     * 配置过滤器
     */
    @Bean
    public ShiroFilterFactoryBean ShiroFilterFactoryBean(SecurityManager securityManager) {

        // 自定过滤器
        LoginLimitFilter loginLimitFilter = loginLimitFilter();

        LinkedHashMap<String, Filter> filters = Maps.newLinkedHashMap();
        filters.put(loginLimitFilter.getName(), loginLimitFilter);

        // 访问过滤
        Map<String, String> filterMap = Maps.newLinkedHashMap();

        // 无需授权路径
        String anon = DefaultFilter.anon.name();
        filterMap.put("/static/**", anon);
        filterMap.put("/js/**", anon);
        filterMap.put("/css/**", anon);
        filterMap.put("/img/**", anon);
        filterMap.put("/fonts/**", anon);
        filterMap.put("/plugin/**", anon);
        filterMap.put(LOGIN_PATH, anon + "," + loginLimitFilter.getName());
        filterMap.put("/system/user/rest/login", anon);

        // 注销地址
        filterMap.put("/system/user/logout", DefaultFilter.logout.name());
        // 记住我 或 认证通过
        filterMap.put("/**", DefaultFilter.user.name() + "," + loginLimitFilter.getName());


        ShiroFilterFactoryBean filerFactory = new ShiroFilterFactoryBean();
        filerFactory.setSecurityManager(securityManager);
        filerFactory.setLoginUrl(LOGIN_PATH);
        filerFactory.setSuccessUrl("/index");
        filerFactory.setFilters(filters);
        filerFactory.setFilterChainDefinitionMap(filterMap);
        return filerFactory;
    }


    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(securityManager());
        return factoryBean;
    }


    /**
     * 配置安全管理
     */
    @Bean
    public SecurityManager securityManager() {

        // 配置认证匹配器
        UserRealm userRealm = realm();
        EhCacheManager ehCacheManager = ehCacheManager();
        RetryLimitMatcher matcher = new RetryLimitMatcher(ehCacheManager);
        matcher.setHashAlgorithmName(SHIRO_ALGORITHM);
        matcher.setHashIterations(SHIRO_ITERATIONS);
        userRealm.setCredentialsMatcher(matcher);

        // 配置安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(ehCacheManager);
        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 配置自定义 验证/授权 realm
     */
    @Bean
    public UserRealm realm() {
        return new UserRealm() {{
            setName(USER_REALM_NAME);
            setCachingEnabled(true);

            // 授权缓存
            setAuthorizationCachingEnabled(true);
            setAuthorizationCacheName(AUTHORIZATION_CACHE_NAME);

            // 认证缓存
            setAuthenticationCachingEnabled(true);
            setAuthenticationCacheName(AUTHENTICATION_CACHE_NAME);
        }};
    }

    /**
     * 配置缓存管理
     * 一次授权多次使用
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * 账号登录限制
     */
    @Bean
    public LoginLimitFilter loginLimitFilter() {
        LoginLimitFilter loginLimitFilter = new LoginLimitFilter()
                .setCacheManager(ehCacheManager())
                .setSessionManager(sessionManager());
        loginLimitFilter.setOutUrl(LOGIN_PATH + "?" + loginLimitFilter.getName());
        return loginLimitFilter;
    }


    /**
     * cookie记住我管理对象
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(AesUtil.hexStrToByte(REMEMBER_ME_CIPHER_KEY));
        return cookieRememberMeManager;
    }


    /**
     * 配置session管理器
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Collection<SessionListener> listeners = Lists.newArrayList(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(ehCacheManager());


        sessionManager.setGlobalSessionTimeout(1800000);        // session 无响应过期时间 30分钟
        sessionManager.setSessionValidationInterval(900000);   // session 过期验证周期 15分钟
        sessionManager.setSessionIdUrlRewritingEnabled(false);  // 取消url后JSESSIONID
        return sessionManager;
    }


    /**
     * 配置session监听
     */
    @Bean
    public UserSessionListener sessionListener() {
        return new UserSessionListener();
    }

    /**
     * session 持久化
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setCacheManager(ehCacheManager());
        enterpriseCacheSessionDAO.setActiveSessionsCacheName(SESSION_CACHE_NAME);
        enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }

    /**
     * 配置sessionID生成器
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


    /**
     * sessionId cookie对象
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        simpleCookie.setMaxAge(-1); // 浏览器关闭时失效此Cookie
        return simpleCookie;
    }


    /**
     * 记住我 cookie对象
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie(REMEMBER_NAME); // 默认为: JSESSIONID,与SERVLET容器名冲突,重新定义rememberMe
        simpleCookie.setHttpOnly(true); // 只http访问,js不可访问,防止xss读取cookie
        simpleCookie.setPath("/");
        simpleCookie.setMaxAge(2592000); // 30天
        return simpleCookie;
    }


    /**
     * thymeleaf 中使用shiro标签
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 启用注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor Advisor = new AuthorizationAttributeSourceAdvisor();
        Advisor.setSecurityManager(securityManager);
        return Advisor;
    }

}