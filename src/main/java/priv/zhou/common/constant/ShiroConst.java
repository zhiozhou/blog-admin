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
     * 用户realm名称
     */
    String USER_REALM_NAME = "userRealm";

    /**
     * 授权缓存名
     */
    String AUTHORIZATION_CACHE_NAME = "authorizationCache";

    /**
     * 认证缓存名
     */
    String AUTHENTICATION_CACHE_NAME = "authenticationCache";


    /**
     * session缓存名
     */
    String SESSION_CACHE_NAME = "activeSessionCache";

    /**
     * session踢出标识
     */
    String SESSION_KICK_OUT_KEY = "KICK_OUT";


    String REMEMBER_NAME = "rememberMe";

    /**
     * 记住我 密钥 必须为8的倍数
     */
    String REMEMBER_ME_CIPHER_KEY = "A94777C22CF1DF51FBBBA33FE221EE55";

}
