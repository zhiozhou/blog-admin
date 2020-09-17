package priv.zhou.common.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 排序通用数据传输
 * @author zhou
 */
@Getter
@Setter
public class Order {

    private String by;

    private String type = "DESC";
}
