package priv.zhou.module.system.user.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.role.domain.vo.RoleVO;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
public class UserVO {

    private Integer id;

    private String username;

    private String name;

    private String phone;

    private Integer state;

    private String stateStr;

    private Date gmtCreate;

    private List<RoleVO> roles;

    private Set<Integer> roleIdSet;

}
