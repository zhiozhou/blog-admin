package priv.zhou.module.blog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.constant.Update;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.blog.domain.po.BlogPO;

import javax.naming.Name;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 博客 数据传输模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogDTO extends DTO<BlogPO> {

    /**
     *
     */
    @NotNull(message = "博客标识不可为空", groups = {Update.class})
    private Integer id;

    /**
     * 标题
     */
    @NotBlank(message = "标题不可为空")
    private String title;

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
     * 备注
     */
    private String remark;

    /**
     * 摘要
     */
    private String abs;

    /**
     * 标签列表
     */
    @NotNull(message = "标签不可为空")
    private List<String> tags;
}
