package priv.zhou.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局接口异常
 *
 * @author zhou
 * @since 2019.05.19
 * @see GlobalHandler#restHandle(HttpServletRequest, RestException)
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

    public RestException(ResultEnum resultEnum, String... holders) {
        this.result = Result.fail(resultEnum, holders);
    }

    public RestException(String info) {
        this.result = Result.fail(info);
    }

}
