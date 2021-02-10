package priv.zhou.module.system.user.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserQuery {

    private Integer id;

    private Integer ridId;

    private Integer state;

    private String username;

    private String usernameLike;

    private String name;

    private String nameLike;

    private String phone;

    private String phoneLike;

    private List<Integer> inRoles;
}
