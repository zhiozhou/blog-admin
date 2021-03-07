package priv.zhou.module.blog.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 标签 数据查询领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class TagQuery {

    private Integer id;

    private String name;

    private String nameLike;
}