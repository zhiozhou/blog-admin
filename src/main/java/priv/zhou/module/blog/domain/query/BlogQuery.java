package priv.zhou.module.blog.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 博客 数据查询领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogQuery {

    private Integer id;

    private Integer ridId;

    private Integer state;

    private String title;

    private String titleLike;

    private String remarkLike;
}