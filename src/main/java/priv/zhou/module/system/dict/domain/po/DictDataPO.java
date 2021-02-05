package priv.zhou.module.system.dict.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典数据 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictDataPO implements Serializable{

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 标识码
	 */
	private String code;

	/**
	 * 标签
	 */
	private String label;

	/**
	 * 字典标识
	 */
	private String dictKey;

	/**
	 * 类型 0正常 1预留 11系统状态
	 */
	private Integer type;


	/**
	 * 默认 0正常 1默认
	 */
	private Integer top;


	/**
	 * 备用
	 */
	private String spare;

}
