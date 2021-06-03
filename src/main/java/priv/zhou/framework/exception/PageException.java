package priv.zhou.framework.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.enums.PageEnum;

/**
 * 页面请求异常
 *
 * @author zhou
 * @since 2020.02.07
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
