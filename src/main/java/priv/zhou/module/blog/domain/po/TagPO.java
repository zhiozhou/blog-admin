package priv.zhou.module.blog.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;

/**
 * 博客类型 数据持久化模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Getter
@Setter
@Accessors(chain = true)
public class TagPO implements Serializable{


	/**
	 * 
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

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
	private Date gmtCreate;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}


}
