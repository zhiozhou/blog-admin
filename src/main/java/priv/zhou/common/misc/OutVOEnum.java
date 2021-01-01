package priv.zhou.common.misc;

/**
 * 全局返回Code枚举类
 * @author  zhou
 * @since 2019.03.11
 */
public enum OutVOEnum {

	SUCCESS("0000", "请求成功"),

	EMPTY_PARAM("0001", "参数为空"),

	FAIL_PARAM("0002", "参数错误"),

	FAIL_OPERATION("0003","操作失败"),

	EMPTY_DATA("0004","数据不存在"),

	FAIL_DATA("0005","数据异常"),


	EXIST_NAME("0101", "名称已存在"),

	EXIST_KEY("0102", "标识值已存在"),

	EXIST_RELATION("0103", "存在关联数据"),


	NONE_USERNAME("1001","用户名不存在"),

	FAIL_LOGIN("1002","用户名或密码错误"),

	LOCKED_USERNAME("1003","账号已被锁定，请稍后再试"),


	ILLEGAL_VISIT("9000", "非法访问"),

	LATER_RETRY("9001", "请稍后重试"),

	OFTEN_OPERATION("9002", "操作频繁"),

	RESPONSE_TIMEOUT("9003", "响应超时"),

	ERROR_SYSTEM("9999", "系统异常");


	OutVOEnum(String code, String info) {
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
}