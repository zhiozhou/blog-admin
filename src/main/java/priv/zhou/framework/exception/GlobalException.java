package priv.zhou.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Result;
import priv.zhou.common.misc.ResultEnum;

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
    private Result<?> result;

    public GlobalException(Result<?> result) {
        this.result = result;
    }

    public GlobalException(ResultEnum resultEnum) {
        this.result = Result.fail(resultEnum);
    }

    public GlobalException(ResultEnum resultEnum, String info) {
        this.result = Result.fail(resultEnum, info);
    }

}
