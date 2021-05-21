package priv.zhou.common.enums;

/**
 * 异常页面枚举
 *
 * @author zhou
 * @since 0.1.0
 */
public enum PageEnum {

    NOT_FOUND("error/404"),

    SERVER_ERROR("error/500"),

    PERMISSION_DENIED("error/denied"),

    RETRY("error/retry");

    PageEnum(String path) {
        this.path = path;
    }

    /**
     * 页面路径
     */
    private final String path;

    public String getPath() {
        return path;
    }
}
