package priv.zhou.module.system.role.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

/**
 * 角色关联菜单 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class RoleMenuPO implements Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UOD;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer menuId;
}
