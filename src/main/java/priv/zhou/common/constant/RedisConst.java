package priv.zhou.common.constant;

/**
 * Redis缓存常量
 *
 * @author zhou
 * @since 0.1.0
 */
public interface RedisConst {

    /**
     * 管理端前缀
     */
    String REDIS_BA_PREFIX = "REDIS_BLOG_ADMIN_PREFIX_";

    /**
     * 服务端前缀
     */
    String REDIS_BS_PREFIX = "REDIS_BLOG_SERVICE_PREFIX_";

    /**
     * 文件上传前缀
     */
    String REDIS_FS_PREFIX = "REDIS_FILE_SERVICE_PREFIX_";


    //----------------------------------------------------------------------    管理端key    ----------------------------------------------------------------------

    /**
     * 异常统计key（Map类型）
     */
    String EXCEPTION_COUNT_KEY = REDIS_BA_PREFIX + "EXCEPTION_COUNT_KEY";

    /**
     * 异常发送key
     * key + 异常类名称
     */
    String EXCEPTION_SENT_KEY = REDIS_BA_PREFIX + "EXCEPTION_SEND_KEY_";

    /**
     * 表单重复提交
     * key + url + MD5(参数json)
     */
    String REDIS_CHECK_REPEAT_KEY = REDIS_BA_PREFIX + "CHECK_REPEAT_KEY";


    //----------------------------------------------------------------------    服务端key    ----------------------------------------------------------------------

    /**
     * 服务端黑名单key（Set类型）
     */
    String BS_BLOCK_IP_SET_KEY = REDIS_BS_PREFIX + "BLOCK_IP_SET";

    /**
     * 服务端菜单key
     */
    String BS_MENU_KEY = REDIS_BS_PREFIX + "MENU_KEY";

    /**
     * 服务端菜单key
     */
    String BS_MENU_MODIFIED_KEY = BS_MENU_KEY + "MODIFIED_KEY";

    /**
     * 服务端字典数据key
     * key + dictKey
     */
    String BS_DICT_DATA_KEY = REDIS_BS_PREFIX + "DICT_DATA_KEY_";

    /**
     * 服务端字典更新时间key
     * key + dictKey
     */
    String BS_DICT_DATA_MODIFIED_KEY = BS_DICT_DATA_KEY + "MODIFIED_KEY";

    /**
     * 文件上传IP白名单（Set类型）
     */
    String FS_WHITE_IP_SET_KEY = "FILE_UPLOAD_REDIS_IP_SET";





}
