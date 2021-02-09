package priv.zhou.framework.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.zhou.common.properties.ShiroProperties;
import priv.zhou.common.tools.AesUtil;
import priv.zhou.framework.shiro.AttemptLimitMatcher;
import priv.zhou.framework.shiro.SyncLoginFilter;
import priv.zhou.framework.shiro.UserRealm;
import priv.zhou.framework.shiro.session.ShiroSessionDAO;
import priv.zhou.framework.shiro.session.ShiroSessionListener;
import priv.zhou.framework.shiro.session.ShiroSessionManager;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static priv.zhou.common.constant.ShiroConst.*;

@Configuration
public class ShiroConfigurer {

    @Value("${spring.redis.host}")
    private String RedisHost;

    @Value("${spring.redis.database}")
    private int RedisDB;

    @Value("${spring.redis.port}")
    private int RedisPort;

    @Value("${spring.redis.password}")
    private String RedisPwd;

    @Autowired
    private ShiroProperties shiroProperties;

    /**
     * 配置过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        // 自定过滤器
        LinkedHashMap<String, Filter> filters = Maps.newLinkedHashMap();

        SyncLoginFilter syncLoginFilter = loginLimitFilter();
        filters.put(syncLoginFilter.getName(), syncLoginFilter);

        // 访问过滤
        Map<String, String> filterMap = Maps.newLinkedHashMap();
        String anon = DefaultFilter.anon.name();
        // 无需授权
        filterMap.put("/static/**", anon);
        filterMap.put("/js/**", anon);
        filterMap.put("/css/**", anon);
        filterMap.put("/img/**", anon);
        filterMap.put("/fonts/**", anon);
        filterMap.put("/plugin/**", anon);
        filterMap.put(LOGIN_PATH, anon + "," + syncLoginFilter.getName());
        filterMap.put("/system/user/rest/login", anon);

        // 注销地址
        filterMap.put("/system/user/logout", DefaultFilter.logout.name());
        // 记住我 或 认证通过
        filterMap.put("/**", DefaultFilter.user.name() + "," + syncLoginFilter.getName());


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
        RedisCacheManager cacheManager = cacheManager();
        AttemptLimitMatcher matcher = new AttemptLimitMatcher(
                shiroProperties.getAttemptLoginLimit(),
                cacheManager.getCache(shiroProperties.getAttemptLoginCacheName()));
        matcher.setHashAlgorithmName(SHIRO_ALGORITHM);
        matcher.setHashIterations(SHIRO_ITERATIONS);
        UserRealm userRealm = realm();
        userRealm.setCredentialsMatcher(matcher);

        // 配置安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setCacheManager(cacheManager);
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
            setAuthorizationCacheName(shiroProperties.getAuthorizationCacheName());

            // 认证缓存
            setAuthenticationCachingEnabled(true);
            setAuthenticationCacheName(shiroProperties.getAuthenticationCacheName());
        }};
    }

    /**
     * 账号登录限制
     */
    @Bean
    public SyncLoginFilter loginLimitFilter() {
        RedisCacheManager cacheManager = cacheManager();
        SyncLoginFilter syncLoginFilter = new SyncLoginFilter()
                .setMaxSync(shiroProperties.getSyncLoginLimit())
                .setCacheManager(cacheManager.getCache(shiroProperties.getSyncLoginCacheName()))
                .setSessionManager(sessionManager());
        syncLoginFilter.setOutUrl(LOGIN_PATH + "?" + syncLoginFilter.getName());
        return syncLoginFilter;
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
     * 缓存管理
     */
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setExpire(shiroProperties.getCacheExpire());
        redisCacheManager.setPrincipalIdFieldName(shiroProperties.getCachePrincipalField());
        return redisCacheManager;
    }


    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(RedisHost);
        redisManager.setPort(RedisPort);
        if (StringUtils.isNotBlank(RedisPwd)) {
            redisManager.setPassword(RedisPwd);
        }
        redisManager.setDatabase(RedisDB);
        return redisManager;
    }

    /**
     * 配置session管理器
     */
    @Bean
    public SessionManager sessionManager() {


        ShiroSessionManager sessionManager = new ShiroSessionManager();
        Collection<SessionListener> listeners = new ArrayList<>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setCacheManager(cacheManager());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        sessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }


    /**
     * 配置session监听
     */
    @Bean
    public ShiroSessionListener sessionListener() {
        return new ShiroSessionListener();
    }

    /**
     * session 持久化
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public SessionDAO sessionDAO() {
        ShiroSessionDAO shiroSessionDAO = new ShiroSessionDAO();
        shiroSessionDAO.setCacheManager(cacheManager());
        shiroSessionDAO.setActiveSessionsCacheName(shiroProperties.getSessionCacheName());
        shiroSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return shiroSessionDAO;
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
        SimpleCookie simpleCookie = new SimpleCookie(REMEMBER_COOKIE_NAME); // 默认为: JSESSIONID,与SERVLET容器名冲突,重新定义rememberMe
        simpleCookie.setHttpOnly(true); // 只http访问,js不可访问,防止xss读取cookie
        simpleCookie.setPath("/");
        simpleCookie.setMaxAge(60 * 60 * 24 * 30); // 30天
        return simpleCookie;
    }


//    /**
//     * 解决spring-boot Whitelabel Error Page
//     * @return
//     */
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//        return new EmbeddedServletContainerCustomizer() {
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//
//                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/unauthorized.html");
//                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//                container.addErrorPages(error401Page, error404Page, error500Page);
//            }
//        };
//    }

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