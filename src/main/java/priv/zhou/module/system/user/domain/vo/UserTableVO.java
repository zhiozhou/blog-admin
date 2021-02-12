package priv.zhou.module.system.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户 数据表格渲染领域模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTableVO {

    private Integer id;

    private String username;

    private String name;

    private String phone;

    private Integer state;

    private String roles;
}
