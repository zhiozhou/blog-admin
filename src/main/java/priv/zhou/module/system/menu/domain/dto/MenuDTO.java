package priv.zhou.module.system.menu.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.interfaces.Update;
import priv.zhou.common.domain.Tree;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO extends Tree {

    /**
     * id
     */
    @NotNull(message = "ID不可为空", groups = Update.class)
    private Integer id;

    /**
     * 父级id
     */
    @NotNull(message = "父级id不可为空")
    private Integer parentId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路径
     */
    private String path;

    /**
     * 状态 0 正常 11隐藏
     */
    @NotNull(message = "状态不可为空")
    private Integer state;

    /**
     * 类型 0 目录 1 菜单 2按钮
     */
    @NotNull(message = "类型不可为空")
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 权限标识
     */
    private String key;

    /**
     * 旗帜 区分前后台菜单
     */
    private Integer flag;

}