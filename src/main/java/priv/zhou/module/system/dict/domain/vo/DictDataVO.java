package priv.zhou.module.system.dict.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictDataVO {

    private String code;

    private String label;

    private Integer type;

    private Boolean defa;

    private String extend;

}