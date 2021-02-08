package priv.zhou.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 活动错误异常
 * 用于活动实体解析时进行抛出
 */
@Getter
@Setter
@Accessors(chain = true)
public class NotFoundException extends RuntimeException {

}
