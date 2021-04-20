package priv.zhou.module.system.user.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 用户 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserRolePO implements Serializable {


    public static final long serialVersionUID = SERIAL_VERSION_UID;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 删除位
     */
    private Boolean deleted;
}
