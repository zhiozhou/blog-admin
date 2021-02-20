package priv.zhou.module.system.menu.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Tree;
import priv.zhou.module.system.menu.domain.po.MenuPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO extends Tree {

    /**
     * id
     */
    private Integer id;

    /**
     * 去除id
     */
    private Integer exclId;

    /**
     * 父级id
     */
    @NotNull(message = "父级id不可为空")
    private Integer parentId;

    /**
     * 父级名称
     */
    private String parentName;

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

    /**
     * 查找的范围
     */
    private List<Integer> types;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 子级列表
     */
    private List<MenuDTO> childList;

    /**
     * 是否选中
     */
    private Boolean checked;

}