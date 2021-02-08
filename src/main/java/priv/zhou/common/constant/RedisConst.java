package priv.zhou.common.constant;

public interface RedisConst {


    //----------------------------------------------------------------------    服务端缓存key    ----------------------------------------------------------------------

    String BS_PREFIX = "BLOG_REDIS_PREFIX_";

    String BS_BLOCK_IP_SET_KEY = BS_PREFIX + "BLOCK_IP_SET";

    String BS_MENU_KEY = BS_PREFIX + "MENU_KEY";

    String BS_MENU_MODIFIED_KEY = BS_MENU_KEY + "MODIFIED_KEY";

    String BS_DICT_DATA_KEY = BS_PREFIX + "DICT_DATA_KEY_";

    String BS_DICT_DATA_MODIFIED_KEY = BS_DICT_DATA_KEY + "MODIFIED_KEY";

    String FILE_SERVICE_IP_SET_KEY = "FILE_UPLOAD_REDIS_IP_SET_";

}
