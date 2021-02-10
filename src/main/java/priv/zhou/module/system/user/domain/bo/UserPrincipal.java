package priv.zhou.module.system.user.domain.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 用户 主要信息 用于shiro验证
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserPrincipal implements Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UID;

    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String name;

    private String phone;

    private Integer state;

    private String roleNames;

}
