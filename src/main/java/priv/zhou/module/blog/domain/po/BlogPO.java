package priv.zhou.module.blog.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *  数据持久化模型
 *
 * @author zhou
 * @since 2020.05.15
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogPO implements Serializable{


	/**
	 * 
	 */
	private Integer id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 文章类型
	 */
	private Integer type;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 博客状态
	 */
	private Integer state;

	/**
	 * 页面访问量
	 */
	private Long pv;

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
