package priv.zhou.module.system.role.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.role.domain.po.RolePO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO extends DTO<RolePO> implements Serializable {

    /**
     * id
     */
    private Integer noid;

    /**
     * id
     */
    private Integer id;

    /**
     * 角色标识
     */
    private String key;

    /**
     * 名称
     */
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 模糊名称
     */
    private String nameLike;


    /**
     * 菜单列表
     */
    private List<MenuDTO> menuList;

    /**
     * 状态 0 正常 11隐藏
     */
    @NotNull(message = "状态不可为空")
    private Integer state;

    /**
     * 状态字符
     */
    private String stateStr;


    /**
     * 备注
     */
    private String remark;

    public RoleDTO(RolePO rolePO) {
        super(rolePO);
        this.menuList  = ofPO(rolePO.getMenuList(), MenuDTO::new);
    }

}
