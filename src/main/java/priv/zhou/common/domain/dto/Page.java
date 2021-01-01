package priv.zhou.common.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页通用数据传输
 *
 * @author zhou
 */
@Getter
@Setter
public class Page {

    private Integer page = 1;

    private Integer limit = 10;

}
