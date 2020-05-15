package priv.zhou.module.system.role.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.po.MenuPO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class RolePO implements Serializable {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 角色标识
	 */
	private String key;

	/**
	 * 名称
	 */
	private String name;


	/**
	 * 菜单列表
	 */
	private List<MenuPO> menuList;

	/**
	 * 状态 0 正常 11隐藏
	 */
	private Integer state;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建人id
	 */
	private Integer createId;

	/**
	 * 修改人id
	 */
	private Integer modifiedId;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

}
