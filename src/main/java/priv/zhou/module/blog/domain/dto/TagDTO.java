package priv.zhou.module.blog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.blog.domain.po.TagPO;

import java.util.Date;


/**
 * 博客类型 数据传输模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDTO extends DTO<TagPO> {


    /**
     *
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    private String nameLike;

    /**
     * 计数
     */
    private Integer count;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;


    public TagDTO(TagPO tagPO) {
        super(tagPO);
    }

}
