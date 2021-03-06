package priv.zhou.module.system.role.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * 角色 数据查询模型
 *
 * @author zhou
 * @since 2021.02.19
 */
@Getter
@Setter
@Accessors(chain = true)
public class RoleQuery {

    private Integer id;

    private Integer ridId;

    private String key;

    private String keyLike;

    private String name;

    private String nameLike;

    private Integer state;

    private String remark;

    private Integer createBy;

}
