package priv.zhou.module.system.role.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 角色 数据持久化模型
 *
 * @author zhou
 * @since 2021.02.19
 */
@Getter
@Setter
@Accessors(chain = true)
public class RolePO implements Serializable{

	public static final long serialVersionUID = SERIAL_VERSION_UID;

	private Integer id;

	/**
	 * key
	 */
	private String key;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 状态 关联字典
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人id
	 */
	private Integer createBy;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改人id
	 */
	private Integer modifiedBy;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 删除位
	 */
	private Boolean deleted;

}
