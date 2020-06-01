package priv.zhou.module.blog.blog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.blog.blog.domain.po.BlogPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 博客 数据传输模型
 *
 * @author zhou
 * @since 2020.05.15
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogDTO extends DTO<BlogPO> {


    /**
     * 
     */
    private Integer id;

    /**
     * 标题
     */
    @NotBlank(message = "标题不可为空")
    private String title;

    private String titleLike;

    /**
     * 文章类型
     */
    @NotNull(message = "类型不可为空")
    private String type;

    private String typeName;


    /**
     * 内容
     */
    @NotBlank(message = "内容不可为空")
    private String content;

    /**
     * 预览
     */
    @NotBlank(message = "预览不可为空")
    private String preview;

    /**
     * 备注
     */
    private String remark;

    /**
     * 页面访问量
     */
    private Long pv;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


    public BlogDTO(BlogPO blogPO) {
        super(blogPO);
    }

}
