package priv.zhou.common.enums;

import priv.zhou.common.domain.Result;

/**
 * 全局返回Code枚举类
 *
 * @author zhou
 * @since 2019.03.11
 */
public enum ResultEnum {

    SUCCESS("0000", "请求成功"),

    EMPTY_PARAM("0001", "参数为空"),

    FAIL_PARAM("0002", "参数错误"),

    EMPTY_DATA("0003", "数据不存在"),

    FAIL_DATA("0004", "数据异常"),

    EXIST_RELATION("0005", "存在关联数据"),

    EXIST_NAME("0006", "名称已存在"),

    EXIST_KEY("0007", "标识值已存在"),

    EXIST_PHONE("0008", "手机号已存在"),

    REPEAT_KEY("0101", "标识值重复"),




    NONE_USERNAME("1001", "用户名不存在"),

    FAIL_LOGIN("1002", "用户名或密码错误"),

    FROZE_USERNAME("1003", "账号已被冻结"),

    LOCKED_USERNAME("1004", "账号已被锁定，请稍后再试"),


    ILLEGAL_VISIT("9000", "非法访问"),

    LATER_RETRY("9001", "请稍后重试"),

    OFTEN_OPERATION("9002", "操作频繁"),

    TIMEOUT_RESPONSE("9003", "响应超时"),

    ERROR_SYSTEM("9999", "系统异常");


    ResultEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * 异常编码
     */
    private final String code;
    /**
     * 异常信息
     */
    private final String info;

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public boolean equals(Result<?> result) {
        return this.equals(result.getCode());
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }
}
