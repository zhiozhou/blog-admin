package priv.zhou.module.blog.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


/**
 * 博客 数据渲染领域模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogVO {

    private Integer id;

    private String title;

    private String content;

    private Integer state;

    private String remark;

    private String abs;

    private Integer pv;

    private String preview;

    private Date gmtCreate;

    private List<TagDTO> tags;


}