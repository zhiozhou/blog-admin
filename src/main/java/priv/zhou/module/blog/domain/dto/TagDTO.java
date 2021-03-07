package priv.zhou.module.blog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.blog.domain.po.TagPO;

import javax.validation.constraints.NotBlank;


/**
 * 博客类型 数据传输模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Getter
@Setter
@Accessors(chain = true)
public class TagDTO extends DTO<TagPO> {

    /**
     * 名称
     */
    @NotBlank(message = "标签名称不可为空")
    private String name;

}
