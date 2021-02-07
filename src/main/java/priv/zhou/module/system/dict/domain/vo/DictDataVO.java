package priv.zhou.module.system.dict.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 字典 数据渲染领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictDataVO {

    private String code;

    private String label;

    private Integer type;

    private Integer top;

    private String spare;

}