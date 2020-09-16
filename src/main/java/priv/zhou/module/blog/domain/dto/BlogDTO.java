package priv.zhou.module.blog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.blog.domain.po.BlogPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 博客 数据传输模型
 *
 * @author zhou
 * @since 2020.09.14
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


    private Integer noid;

    /**
     * 标题
     */
    @NotBlank(message = "标题不可为空")
    private String title;

    private String titleLike;

    /**
     * 内容
     */
    @NotBlank(message = "内容不可为空")
    private String content;

    /**
     * 预览（json）
     */
    private String preview;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 标签列表
     */
    private List<TagDTO> tags;

    /**
     * 备注
     */
    private String remark;

    private String remarkLike;

    /**
     * 摘要
     */
    private String abs;

    /**
     * 页面访问量
     */
    private Long pv;

    /**
     * 创建人
     */
    private Integer createId;

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
        if(null != blogPO.getTags()){
            this.tags = blogPO.getTags().stream().map(TagDTO::new).collect(Collectors.toList());
        }
    }

    @Override
    public BlogPO toPO() {
        return super.toPO()
                .setTags(tags.stream().map(TagDTO::toPO).collect(Collectors.toList()));
    }
}
