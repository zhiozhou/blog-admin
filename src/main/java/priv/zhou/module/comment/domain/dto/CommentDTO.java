package priv.zhou.module.comment.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.comment.domain.po.CommentPO;
import priv.zhou.module.visitor.domain.dto.VisitorDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * 评论 数据传输模型
 *
 * @author zhou
 * @since 2020.09.17
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO extends DTO<CommentPO> {


    /**
     *
     */
    private Integer id;

    /**
     * 主题id
     */
    private Integer topicId;

    /**
     * 回复评论id
     */
    @NotNull(message = "回复评论不可为空")
    private Integer repliedId;

    /**
     * 目标访客id
     */
    private Integer toVid;

    /**
     * 访客id
     */
    private Integer fromVid;

    /**
     * 内容
     */
    @NotBlank(message = "内容不可为空")
    private String content;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态字符
     */
    private String stateStr;

    /**
     * IP
     */
    private String ip;

    /**
     * 用户代理
     */
    private String ua;



    /**
     * 博客
     */
    private BlogDTO blog;

    /**
     * 访客
     */
    private VisitorDTO fromVisitor;


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


    public CommentDTO(CommentPO commentPO) {
        super(commentPO);
        this.blog = ofPO(commentPO.getBlog(), BlogDTO::new);
        this.fromVisitor = ofPO(commentPO.getFromVisitor(), VisitorDTO::new);
    }

    @Override
    public CommentPO toPO() {
        return super.toPO()
                .setBlog(toPO(blog))
                .setFromVisitor(toPO(fromVisitor));
    }

}
