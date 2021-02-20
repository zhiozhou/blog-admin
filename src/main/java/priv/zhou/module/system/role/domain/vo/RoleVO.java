package priv.zhou.module.system.role.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.Accessors;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import priv.zhou.module.system.menu.domain.vo.MenuVO;

/**
 * 角色 数据持久化模型
 *
 * @author zhou
 * @since 2021.02.19
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVO {

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
	 * 状态名称
	 */
	private String stateStr;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 绑定菜单
	 */
	private List<MenuVO> menus;
}
