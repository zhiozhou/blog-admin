package priv.zhou.common.param;


import java.util.HashMap;
import java.util.Map;

public interface CONSTANT {

    String DEFAULT_FILL = "0";


    /**
     * 登陆机智
     */
    String LOGIN_PATH = "/system/user/login";

    /**
     * 站长id
     */
    Integer ZHOU_VISITOR_ID = 0;


    String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnhWMXpLf1LwEY5mO6JO4vdCgYS+S/VV6x5Uz2LkYOM5SOAdWwRH6SjU9Pofi8xAl0xZ+Ohtpo36WbjdCeZORStoQMkV6Pq7Z1K47s/wxoy2NwglvJNuWtk4fIIeH3X9I6M8mumnEKsOBtI3yXsFHt0qG+2ktTalBgwefzVlqVxD2c1QcggmoCjCCmNAzdLFDQJZqTjH0KRqlTROMxT3kOUCRft2UnWpRzSYvicjvRvXYfkRE5lhmhxsRhS35umrRh7k1oTOtm9oWa19IGw3oyDlttNckVbVun+zW4Up3830dggGo1OnjnoZES+nRFjApckExfIbX9+NU2Gs/B0q96QIDAQAB";

    String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCeFYxekt/UvARjmY7ok7i90KBhL5L9VXrHlTPYuRg4zlI4B1bBEfpKNT0+h+LzECXTFn46G2mjfpZuN0J5k5FK2hAyRXo+rtnUrjuz/DGjLY3CCW8k25a2Th8gh4fdf0jozya6acQqw4G0jfJewUe3Sob7aS1NqUGDB5/NWWpXEPZzVByCCagKMIKY0DN0sUNAlmpOMfQpGqVNE4zFPeQ5QJF+3ZSdalHNJi+JyO9G9dh+RETmWGaHGxGFLfm6atGHuTWhM62b2hZrX0gbDejIOW201yRVtW6f7NbhSnfzfR2CAajU6eOehkRL6dEWMClyQTF8htf341TYaz8HSr3pAgMBAAECggEAQDYIejG2cTadoccx4Q5/r7oaKt+kGPqrkH3uPU2RpSFJDDEO8AVZYOMXKVsQuCIK+x7bzsZrtlkoyUPw4GksTvccNQ87hFKJcHYFEiqSDvHl7rbeSl5XbV12D0R9z15wQwBBft6PK7+V47z0gcCIXM8M3PnvyOfDEHH8WvDC3PIHd7AsIH+Dbxy1g7KhUg+0oB+P/PXD4WkdJHQ/kv5m5eKFxO852B/aa6lBHphNLEpATtUQWL08gwjcTQ0oNg6dnKJ42E5oVbl78/lWdxDNbGrJqcQxfbJFWAS1XOC/3JTNQJlmPOM4UozkzaoQyP8F2ICvvSCOPGv1K62W8PMBwQKBgQDMA0kjXVea2NnfWh5CuMTzxTZEafI/1ADlMuxlCpJg9ms1CO3mJ2BglkKfnH6RebCGWvTf1XHf3dKS0XsX0juNlI6APotexcL+ll/wo035Vp/B/4CX0hAyN1A8OSMNL5SGb3GdCmfkRA1/zIPCGmrcOdmzI8YVQmY2iKyzwO4CbQKBgQDGXh7c0y/NRfSd7rhayCVU6FAu2XYM+BO5lgr1WCLbz2Edw6FzYaxdgSwkfTi2TzgLO3RU0MKfvUXistalGQLM5pZTa74wj61yGc2UeRpn34mv0auvJ7X6ffrV9UyRwiH3OjJtg7bDU84h/LKVvlcamhY/jPNGOCQg0Ly1/NUb7QKBgA4h/CMTCEqV0yIB4C7HFf29H7FFlZ6OVTGLgYzOWq2U5IBcCf7zo12Nizf7Y1k4kN391xDafHUBrOC22kwgKDf6npSLwKPkF0oDqMnGxlQLiC2sQYBC8RRJeKlzW9ZCAYv+lGRIoEHT5QuBY29C3Tw8VbRvOf2nB+WnOi+JofNRAoGBAJOqaOsVwtXaJ5iRpmbd6vxj25ykgP5yhax3hzzQGVLr822Gvd74bKlGiSe4l8kO+7dQ0O56YQcsjKOPYb2Q+saxOGJtKC1aJ1Fy59iItiFxBDv/pwBCXXNU6K7xZchzbXluty9TB3AsKJygUgiqoojI/5rbQBWboTlUacZHqhSZAoGBAMVqCG+Uf1m25Lmuv5qa2OqzbYicD8vVbMnQea4zzuUNeewShp1krCbxzabDBHy3qAfihoCEVuILRLuwzSAtOOa4NM0jwwaBFMjqc6YNN4o+nkDQX2ZbgBoYjIlU4BCXJzPOi2wRodfLHZ1q7VFX5A/Xlqq3vGUjfYm3wGXfTHWB";


    //----------------------------------------------------------------------    服务端缓存key    ----------------------------------------------------------------------

    String BLOG_SERVICE_PREFIX = "BLOG_REDIS_PREFIX_";

    String BLOG_SERVICE_BLOG_KEY = BLOG_SERVICE_PREFIX + "BLOG_KEY_";

    String BLOG_SERVICE_BLOG_TYPE_KEY = BLOG_SERVICE_PREFIX + "BLOG_TYPE_KEY_";

    String BLOG_SERVICE_MENU_KEY = BLOG_SERVICE_PREFIX + "MENU_KEY";

    String BLOG_SERVICE_MENU_MODIFIED_KEY = BLOG_SERVICE_MENU_KEY + "MODIFIED_KEY";

    String BLOG_SERVICE_DICT_DATA_KEY = BLOG_SERVICE_PREFIX + "DICT_DATA_KEY_";

    String BLOG_SERVICE_DICT_DATA_MODIFIED_KEY = BLOG_SERVICE_DICT_DATA_KEY + "MODIFIED_KEY";

    String FILE_SERVICE_IP_SET_KEY = "FILE_UPLOAD_REDIS_IP_SET_";

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
