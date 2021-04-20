package priv.zhou.module.system.dict.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 字典 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictPO implements Serializable{

	public static final long serialVersionUID = SERIAL_VERSION_UID;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 字典key
	 */
	private String key;

	/**
	 * 状态 0正常 11停用
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人
	 */
	private Integer createBy;

	/**
	 * 修改人
	 */
	private Integer modifiedBy;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 删除位
	 */
	private Boolean deleted;
}
