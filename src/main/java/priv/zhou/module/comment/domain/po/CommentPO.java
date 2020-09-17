package priv.zhou.module.comment.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.blog.domain.po.BlogPO;
import priv.zhou.module.visitor.domain.po.VisitorPO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论 数据持久化模型
 *
 * @author zhou
 * @since 2020.09.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class CommentPO implements Serializable {

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
    private String content;

    /**
     * 状态
     */
    private Integer state;

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
    private BlogPO blog;

    /**
     * 访客
     */
    private VisitorPO fromVisitor;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
