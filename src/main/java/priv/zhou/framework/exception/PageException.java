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
public class PageException extends RuntimeException {
    private String page;

    public PageException( String page) {
        this.page = page;
    }
}
