package priv.zhou.module.blog.blogType.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import priv.zhou.module.blog.blogType.domain.po.BlogTypePO;
import priv.zhou.common.domain.dto.DTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;


/**
 *  数据传输模型
 *
 * @author zhou
 * @since 2020.05.21
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogTypeDTO extends DTO<BlogTypePO> {


    /**
     * 
     */
    private Integer id;

    private Integer noid;

    /**
     * 标识
     */
    @NotBlank(message = "标识不可为空")
    private String key;

    private String keyLike;

    /**
     * 名称
     */
    @NotBlank(message = "名称不可为空")
    private String name;

    private String nameLike;
    /**
     * 标题
     */
    @NotBlank(message = "标题不可为空")
    private String title;

    private String titleLike;

    /**
     * 类型
     */
    @NotBlank(message = "类型不可为空")
    private String desc;

    /**
     * 背景
     */
    @NotBlank(message = "背景不可为空")
    private String bg;

    /**
     * 备注
     */
    @NotBlank(message = "备注不可为空")
    private String remark;

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


    public BlogTypeDTO(BlogTypePO blogTypePO) {
        super(blogTypePO);
    }

}
