package priv.zhou.module.system.dict.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 字典 数据查询领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictQuery {

    private Integer id;

    private Integer ridId;

    private String name;

    private String nameLike;

    private String key;

    private String keyLike;

    private String state;

}