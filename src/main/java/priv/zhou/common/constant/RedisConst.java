package priv.zhou.common.constant;

public interface RedisConst {


    String BA_PREFIX = "REDIS_BLOG_ADMIN_PREFIX_";


    /**
     * 异常统计key（Map类型）
     */
    String EXCEPTION_COUNT_KEY = BA_PREFIX + "EXCEPTION_COUNT_KEY";

    /**
     * 异常发送key
     * key + 异常名称
     */
    String EXCEPTION_SENT_KEY = BA_PREFIX + "EXCEPTION_SEND_KEY_";


    //----------------------------------------------------------------------    服务端缓存key    ----------------------------------------------------------------------

    String BS_PREFIX = "REDIS_BLOG_SERVICE_PREFIX_";

    String BS_BLOCK_IP_SET_KEY = BS_PREFIX + "BLOCK_IP_SET";

    String BS_MENU_KEY = BS_PREFIX + "MENU_KEY";

    String BS_MENU_MODIFIED_KEY = BS_MENU_KEY + "MODIFIED_KEY";

    String BS_DICT_DATA_KEY = BS_PREFIX + "DICT_DATA_KEY_";

    String BS_DICT_DATA_MODIFIED_KEY = BS_DICT_DATA_KEY + "MODIFIED_KEY";

    String FILE_SERVICE_IP_SET_KEY = "FILE_UPLOAD_REDIS_IP_SET_";





}
