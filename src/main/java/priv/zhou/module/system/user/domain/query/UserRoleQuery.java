package priv.zhou.module.system.user.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


/**
 * 用户角色 数据查询模型
 *
 * @author zhou
 * @since 2021.02.19
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserRoleQuery {

    private Integer userId;

    private Integer roleId;

    private String[] roleKeys;

}
