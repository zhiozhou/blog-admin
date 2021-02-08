package priv.zhou.common.constant;

public interface ShiroConst {


    /**
     * 密码算法
     */
    String SHIRO_ALGORITHM = "MD5";

    /**
     * 密码迭代次数
     */
    int SHIRO_ITERATIONS = 1;


    /**
     * 登录尝试限制
     */
    int LOGIN_ATTEMPT_LIMIT = 5;


    /**
     * 针对不同用户的区分字段
     */
    String CACHE_USER_PRINCIPAL_FILE = "username";

    /**
     * 用户权限缓存时间
     */
    int CACHE_AUTHORIZATION_EXPIRE = 200000;

    /**
     * 用户会话前缀
     */
    String SESSION_KEY_PREFIX = "shiro:session:";


    /**
     * 用户realm名称
     */
    String USER_REALM_NAME = "userRealm";

    /**
     * 授权缓存名
     */
    String AUTHORIZATION_CACHE_NAME = "authorization-cache";

    /**
     * 认证缓存名
     */
    String AUTHENTICATION_CACHE_NAME = "authentication-cache";


    /**
     * session缓存名
     */
    String SESSION_CACHE_NAME = "active-session-sache";

    /**
     * 登录重试限制缓存名
     */
    String RETRY_CACHE_NAME = "retry-cache";

    /**
     * 多点登录限制缓存名
     */
    String LOGIN_LIMIT_CACHE_NAME = "login-limit-cache";

    /**
     * session踢出标识
     */
    String SESSION_KICK_OUT_KEY = "KICK_OUT";


    String REMEMBER_COOKIE_NAME = "rememberMe";

    /**
     * 记住我 密钥 必须为8的倍数
     */
    String REMEMBER_ME_CIPHER_KEY = "A94777C22CF1DF51FBBBA33FE221EE55";

}
