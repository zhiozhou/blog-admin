package priv.zhou.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.enums.PageEnum;

/**
 * 活动错误异常
 * 用于活动实体解析时进行抛出
 */
@Getter
@Setter
@Accessors(chain = true)
public class PageException extends RuntimeException {

    private PageEnum pageEnum;

    public PageException(PageEnum pageEnum) {
        this.pageEnum = pageEnum;
    }
}
