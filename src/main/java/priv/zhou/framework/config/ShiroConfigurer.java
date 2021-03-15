package priv.zhou.framework.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
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
import org.apache.tomcat.util.buf.HexUtils;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.zhou.common.properties.ShiroProperties;
import priv.zhou.framework.shiro.SyncOnlineFilter;
import priv.zhou.framework.shiro.UserCredentialsMatcher;
import priv.zhou.framework.shiro.UserRealm;
import priv.zhou.framework.shiro.session.ShiroSessionDAO;
import priv.zhou.framework.shiro.session.ShiroSessionFactory;
import priv.zhou.framework.shiro.session.ShiroSessionListener;
import priv.zhou.framework.shiro.session.ShiroSessionManager;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

import static priv.zhou.common.constant.ShiroConst.*;

@Configuration
@RequiredArgsConstructor
public class ShiroConfigurer {

    private final ShiroProperties shiroProperties;

    @Value("${spring.redis.host}")
    private String RedisHost;

    @Value("${spring.redis.database}")
    private int RedisDB;

    @Value("${spring.redis.port}")
    private int RedisPort;

    @Value("${spring.redis.password}")
    private String RedisPwd;


    /**
     * 配置过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilte(SecurityManager securityManager) {

        // 自定过滤器
        LinkedHashMap<String, Filter> filters = Maps.newLinkedHashMap();

        SyncOnlineFilter syncOnlineFilter = syncOnlineFilter();
        filters.put(syncOnlineFilter.getName(), syncOnlineFilter);

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
        filterMap.put("/test/**", anon);
        filterMap.put("/system/user/rest/login", anon);
        filterMap.put(LOGIN_PATH, anon);

        // 记住我 或 认证通过
        filterMap.put("/**", DefaultFilter.user.name() + "," + syncOnlineFilter.getName());

        ShiroFilterFactoryBean filerFactory = new ShiroFilterFactoryBean();
        filerFactory.setSecurityManager(securityManager);
        filerFactory.setLoginUrl(LOGIN_PATH);
        filerFactory.setSuccessUrl("/index");
        filerFactory.setFilters(filters);
        filerFactory.setFilterChainDefinitionMap(filterMap);
        return filerFactory;
    }


    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        return new MethodInvokingFactoryBean() {{
            setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
            setArguments(securityManager());
        }};
    }


    /**
     * 配置安全管理
     */
    @Bean
    public SecurityManager securityManager() {
        return new DefaultWebSecurityManager() {{
            setRealm(userRealm());
            setCacheManager(cacheManager());
            setSessionManager(sessionManager());
            setRememberMeManager(rememberMeManager());
        }};
    }

    /**
     * 配置自定义 验证/授权 realm
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm() {{
            setName(USER_REALM_NAME);
            setCachingEnabled(true);
            setCredentialsMatcher(credentialsMatcher());

            // 授权缓存
            setAuthorizationCachingEnabled(true);
            setAuthorizationCacheName(shiroProperties.getAuthorizationCacheName());

            // 认证缓存
            setAuthenticationCachingEnabled(true);
            setAuthenticationCacheName(shiroProperties.getAuthenticationCacheName());
        }};
    }

    /**
     * 配置自定义证书匹配
     */
    @Bean
    public UserCredentialsMatcher credentialsMatcher() {
        return new UserCredentialsMatcher(
                shiroProperties.getAttemptLoginLimit(),
                cacheManager().getCache(shiroProperties.getAttemptLoginCacheName()));
    }

    /**
     * 账号登录限制
     */
    @Bean
    public SyncOnlineFilter syncOnlineFilter() {
        return new SyncOnlineFilter() {{
            setSessionDAO(sessionDAO());
            setOutUrl(LOGIN_PATH + "?" + this.getName());
            setMaxSync(shiroProperties.getSyncOnlineLimit());
            setCache(cacheManager().getCache(shiroProperties.getSyncOnlineCacheName()));
        }};
    }


    /**
     * cookie记住我管理对象
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        return new CookieRememberMeManager() {{
            setCookie(rememberMeCookie());
            setCipherKey(HexUtils.fromHexString(REMEMBER_ME_CIPHER_KEY));
        }};
    }


    /**
     * 缓存管理
     */
    @Bean
    public RedisCacheManager cacheManager() {
        return new RedisCacheManager() {{
            setRedisManager(redisManager());
            setExpire(shiroProperties.getCacheExpire());
            setPrincipalIdFieldName(CACHE_PRINCIPAL_FIELD);
        }};
    }

    /**
     * Redis管理
     */
    @Bean
    public RedisManager redisManager() {
        return new RedisManager() {{
            setHost(RedisHost);
            setPort(RedisPort);
            setDatabase(RedisDB);
            if (StringUtils.isNotBlank(RedisPwd)) {
                setPassword(RedisPwd);
            }
        }};
    }

    /**
     * 配置session管理器
     */
    @Bean
    public SessionManager sessionManager() {
        return new ShiroSessionManager() {{
            setSessionListeners(Lists.newArrayList(sessionListener()));
            setSessionDAO(sessionDAO());
            setDeleteInvalidSessions(true);
            setCacheManager(cacheManager());
            setSessionFactory(sessionFactory());
            setSessionIdCookie(sessionIdCookie());
            setSessionValidationSchedulerEnabled(true);
            setGlobalSessionTimeout(shiroProperties.getSessionExpire());
            setSessionValidationInterval(shiroProperties.getSessionValidInterval());
            setSessionIdUrlRewritingEnabled(false); //取消url 后面的 JSESSIONID
        }};
    }


    /**
     * 配置session监听
     */
    @Bean
    public ShiroSessionListener sessionListener() {
        return new ShiroSessionListener();
    }


    @Bean
    public ShiroSessionFactory sessionFactory() {
        return new ShiroSessionFactory();
    }


    /**
     * session 持久化
     * 提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     */
    @Bean
    public SessionDAO sessionDAO() {
        ShiroSessionDAO sessionDAO = new ShiroSessionDAO(2 * 60 * 1000);
        sessionDAO.setCacheManager(cacheManager());
        sessionDAO.setActiveSessionsCacheName(shiroProperties.getSessionCacheName());
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
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
        return new SimpleCookie(shiroProperties.getSessionIdCookieName()) {{
            setHttpOnly(true);
            setPath("/");
            setMaxAge(-1); // 浏览器关闭时失效此Cookie
        }};
    }


    /**
     * 记住我 cookie对象
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        return new SimpleCookie(shiroProperties.getRememberMeCookieName()) {{
            setHttpOnly(true); // 只http访问,js不可访问,防止xss读取cookie
            setPath("/");
            setMaxAge(shiroProperties.getRememberMeExpire()); // 30天
        }};
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
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

}