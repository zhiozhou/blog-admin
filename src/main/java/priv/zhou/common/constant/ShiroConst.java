package priv.zhou.common.constant;

public interface ShiroConst {


    /**
     * 不同用户的缓存区分字段
     */
    String CACHE_PRINCIPAL_FIELD = "username";

    /**
     * 登陆地址
     */
    String LOGIN_PATH = "/system/user/login";

    /**
     * 用户realm名称
     */
    String USER_REALM_NAME = "user:realm";

    /**
     * 记住我 密钥 必须为8的倍数
     */
    String REMEMBER_ME_CIPHER_KEY = "A94777C22CF1DF51FBBBA33FE221EE55";

}
