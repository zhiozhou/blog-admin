package priv.zhou.module.block.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;

/**
 * 阻塞 数据持久化模型
 *
 * @author zhou
 * @since 2020.09.18
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlockPO implements Serializable{


	/**
	 * 
	 */
	private Integer id;

	/**
	 * IP
	 */
	private String ip;

	private String ipLike;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 原因
	 */
	private String reason;

	private String reasonLike;

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
