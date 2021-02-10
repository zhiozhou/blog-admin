package priv.zhou.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhou
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    /**
     * 缓存过期时间，单位秒
     * 默认为2天
     */
    private Integer cacheExpire = 172800;

    /**
     * 不同用户的缓存区分字段
     */
    private String cachePrincipalField;

    /**
     * 授权缓存名
     */
    private String authenticationCacheName = "authentication-cache";

    /**
     * 权限缓存名
     */
    private String authorizationCacheName = "authorization-cache";

    /**
     * 会话缓存名
     */
    private String sessionCacheName = "active-session-cache";

    /**
     * 尝试登录缓存名
     */
    private String attemptLoginCacheName = "attempt-login-cache";

    /**
     * 同时登录限制缓存名
     */
    private String syncLoginCacheName = "sync-login-cache";

    /**
     * session过期时间
     * 单位毫秒，默认为半小时
     */
    private Integer sessionExpire = 1800000;

    /**
     * session失效扫描间隔,用于清理直接关浏览器造成的孤立会话
     * 单位毫秒，默认为1小时
     */
    private Integer sessionValidInterval = 3600000;

    /**
     * 会话id的cookie名
     */
    private String sessionIdCookieName = "sid";

    /**
     * 会话key的前缀
     */
    private String sessionKeyPrefix = "shiro:session";

    /**
     * 记住我cookie过期时间，单位秒
     * 默认30天
     */
    private Integer rememberMeExpire = 60 * 60 * 24 * 30;

    /**
     * 记住我cookie名
     */
    private String rememberMeCookieName = "rememberMe";

    /**
     * 记住我cookie名称
     */
    String REMEMBER_COOKIE_NAME = "rememberMe";

    /**
     * 尝试登录最大限制
     */
    private Integer attemptLoginLimit;

    /**
     * 同时登录最大限制
     */
    private Integer syncLoginLimit;


}

