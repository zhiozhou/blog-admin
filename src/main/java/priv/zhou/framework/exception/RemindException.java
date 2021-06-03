package priv.zhou.framework.exception;

import lombok.Getter;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * 强提醒异常
 *
 * @author zhou
 * @since 2021/06/03
 * @see GlobalHandler#remindHandle(HttpServletRequest, RemindException)
 */
@Getter
public class RemindException extends RestException {

    private final String remind;

    public RemindException(Result<?> result, String remind) {
        super(result);
        this.remind = remind;
    }

    public RemindException(ResultEnum resultEnum, String remind, String... holders) {
        super(resultEnum, holders);
        this.remind = remind;
    }

    public RemindException(String info, String remind) {
        super(info);
        this.remind = remind;
    }
}
