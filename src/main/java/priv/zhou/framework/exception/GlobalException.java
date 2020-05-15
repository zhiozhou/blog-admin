package priv.zhou.framework.exception;

import lombok.Setter;
import priv.zhou.common.domain.vo.OutVO;
import lombok.Getter;

/**
 * 活动错误异常
 * 用于活动实体解析时进行抛出
 */
@Getter
@Setter
public class GlobalException extends Exception {

	/**
	 * 包含错误信息的vo对象
	 */
	private OutVO<?> outVO;
}
