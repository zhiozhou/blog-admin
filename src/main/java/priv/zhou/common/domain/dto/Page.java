package priv.zhou.common.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页数据传输
 *
 * @author zhou
 * @since 0.1.0
 */
@Getter
@Setter
public class Page {

    private Integer page = 1;

    private Integer limit = 10;

}
