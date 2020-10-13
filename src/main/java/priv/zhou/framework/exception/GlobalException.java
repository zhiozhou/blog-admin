package priv.zhou.framework.exception;

import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.vo.OutVO;
import lombok.Getter;

import java.util.function.Supplier;

/**
 * 活动错误异常
 * 用于活动实体解析时进行抛出
 */
@Getter
@Setter
@Accessors(chain = true)
public class GlobalException extends RuntimeException {

	/**
	 * 包含错误信息的vo对象
	 */
	private OutVO<?> outVO;
}
