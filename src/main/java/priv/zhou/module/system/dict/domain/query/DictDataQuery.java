package priv.zhou.module.system.dict.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 字典数据 数据查询领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictDataQuery {

    private String code;

    private String dictKey;

    private Integer type;

}