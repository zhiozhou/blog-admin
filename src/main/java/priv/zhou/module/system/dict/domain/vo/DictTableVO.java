package priv.zhou.module.system.dict.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 字典 数据表格渲染领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictTableVO {

    private Integer id;

    private String name;

    private String key;

    private Integer state;

    private String remark;

}