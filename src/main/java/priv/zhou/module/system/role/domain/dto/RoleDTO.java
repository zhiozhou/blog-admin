package priv.zhou.module.system.role.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import priv.zhou.common.interfaces.Update;


/**
 * 角色 数据传输模型
 *
 * @author zhou
 * @since 2021.02.19
 */
@Getter
@Setter
@Accessors(chain = true)
public class RoleDTO {

    /**
     * 
     */
    @NotNull(message = "ID不可为空", groups = Update.class)
    private Integer id;

    /**
     * key
     */
    private String key;

    /**
     * 名称
     */
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 状态 关联字典
     */
    @NotNull(message = "状态不可为空")
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 绑定菜单
     */
    private List<Integer> menus;

}
