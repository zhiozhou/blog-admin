package priv.zhou.common.param;


import java.util.HashMap;
import java.util.Map;

public interface CONSTANT {

    String DEFAULT_FILL = "0";


    /**
     * 登陆机智
     */
    String LOGIN_PATH = "/system/user/login";


    //----------------------------------------------------------------------    shiro    ----------------------------------------------------------------------

    /**
     * 密码算法
     */
    String SHIRO_ALGORITHM = "MD5";

    /**
     * 密码迭代次数
     */
    Integer SHIRO_ITERATIONS = 1;


    /**
     * 登录尝试限制
     */
    Integer LOGIN_ATTEMPT_LIMIT = 5;

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
    String SESSION_CACHE_NAME =  "activeSessionCache";

    /**
     * session踢出标识
     */
    String SESSION_KICK_OUT_KEY = "KICK_OUT";


    String REMEMBER_NAME = "rememberMe";

    /**
     * 记住我 密钥 必须为8的倍数
     */
    String REMEMBER_ME_CIPHER_KEY = "A94777C22CF1DF51FBBBA33FE221EE55";

    //----------------------------------------------------------------------    代码生成    ----------------------------------------------------------------------

    Map<String, String> TYPE_MAP = new HashMap<String, String>() {{
        put("tinyint", "Integer");
        put("smallint", "Integer");
        put("mediumint", "Integer");
        put("int", "Integer");
        put("integer", "integer");
        put("bigint", "Long");
        put("float", "Float");
        put("double", "Double");
        put("decimal", "BigDecimal");
        put("bit", "Boolean");
        put("char", "String");
        put("varchar", "String");
        put("tinytext", "String");
        put("text", "String");
        put("mediumtext", "String");
        put("longtext", "String");
        put("date", "Date");
        put("datetime", "Date");
        put("timestamp", "Date");
    }};

    String PRIMARY = "PRI";

    String DEMO_PATH = "src/main/resources/templates/vm";

    String SEPARATOR = "/";


    //----------------------------------------------------------------------    系统字典    ----------------------------------------------------------------------

    String SYSTEM_MENU_TYPE = "system_menu_type";

    String SYSTEM_MENU_STATE = "system_menu_state";

    String SYSTEM_ROLE_STATE = "system_role_state";

    String SYSTEM_USER_STATE = "system_user_state";

}
