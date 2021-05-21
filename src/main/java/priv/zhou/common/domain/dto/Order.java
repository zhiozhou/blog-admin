package priv.zhou.common.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 废弃等待完全删除
 * @deprecated
 */
@Getter
@Setter
public class Order {

    private String by;

    private String type = "DESC";
}
