package priv.zhou.module.access.comment.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import priv.zhou.module.access.comment.domain.dto.CommentDTO;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;
import priv.zhou.module.access.visitor.domain.po.VisitorPO;
import priv.zhou.module.blog.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.blog.domain.po.BlogPO;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论 数据持久化模型
 *
 * @author zhou
 * @since 2020.06.25
 */
@Getter
@Setter
@Accessors(chain = true)
public class CommentPO implements Serializable{

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 博客
	 */
	private BlogPO blog;

	/**
	 * 主题id
	 */
	private Integer topicId;

	/**
	 * 目标访客id
	 */
	private Integer toVid;

	/**
	 * 回复的评论
	 */
	private CommentPO repliedComment;

	/**
	 * 目标访客
	 */
	private VisitorPO toVisitor;

	/**
	 * 访客
	 */
	private VisitorPO fromVisitor;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 状态
	 */
	private Integer state;

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
