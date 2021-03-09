package priv.zhou.module.system.menu.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

@Getter
@Setter
@Accessors(chain = true)
public class MenuPO implements Serializable {


	public static final long serialVersionUID = SERIAL_VERSION_UOD;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 父级id
	 */
	private Integer parentId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 类型 0 目录 1 菜单 2按钮
	 */
	private Integer type;

	/**
	 * 状态 0 正常 11隐藏
	 */
	private Integer state;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 权限标识
	 */
	private String key;

	/**
	 * 旗帜 区分前后台菜单
	 */
	private Integer flag;

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


}
