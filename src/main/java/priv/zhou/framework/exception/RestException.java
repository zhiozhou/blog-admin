package priv.zhou.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;

/**
 * 活动错误异常
 * 用于活动实体解析时进行抛出
 */
@Getter
@Setter
@Accessors(chain = true)
public class RestException extends RuntimeException {

    /**
     * 包含错误信息的vo对象
     */
    private Result<?> result;

    public RestException(Result<?> result) {
        this.result = result;
    }

    public RestException(ResultEnum resultEnum) {
        this.result = Result.fail(resultEnum);
    }

    public RestException(String info) {
        this.result = Result.fail(info);
    }

}
