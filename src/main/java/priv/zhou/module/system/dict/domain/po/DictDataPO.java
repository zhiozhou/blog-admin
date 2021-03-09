package priv.zhou.module.system.dict.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

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

	public static final long serialVersionUID = SERIAL_VERSION_UOD;

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
	 * 首选标志位
	 */
	private Boolean perf;


	/**
	 * 备用
	 */
	private String spare;

	/**
	 * 删除位
	 */
	private Boolean deleted;

}
