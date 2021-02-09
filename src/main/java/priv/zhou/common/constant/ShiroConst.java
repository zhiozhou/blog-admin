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
     * 登陆机智
     */
    String LOGIN_PATH = "/system/user/login";

    /**
     * 用户realm名称
     */
    String USER_REALM_NAME = "user:realm";
    /**
     * 记住我cookie名称
     */
    String REMEMBER_COOKIE_NAME = "rememberMe";

    /**
     * 记住我 密钥 必须为8的倍数
     */
    String REMEMBER_ME_CIPHER_KEY = "A94777C22CF1DF51FBBBA33FE221EE55";

}
