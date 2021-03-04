package priv.zhou.module.system.role.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Tree;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.domain.vo.RoleVO;
import priv.zhou.module.system.role.service.IRoleService;

import static priv.zhou.common.constant.DictConst.SYSTEM_ROLE_STATE;
import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;

/**
 * 角色 视图控制层
 *
 * @author zhou
 * @since 2020.04.15
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IMenuService menuService;

    public RoleController(IRoleService roleService, IMenuService menuService) {
        super("角色", "system:role");
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @RequiresPermissions("system:role:add")
    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new RoleVO().setState(0).setMenus(Lists.newArrayList()));
        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_ROLE_STATE, DICT_NORM_TYPE));
        model.addAttribute("menuTree", menuService.treeSelectVO(new MenuQuery().setFlag(ADMIN_FLAG)));
        return "system/role/au";
    }

    @RequiresPermissions("system:role:update")
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        super.update(model, roleService.getVO(new RoleQuery().setId(id)));

        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_ROLE_STATE, DICT_NORM_TYPE));
        model.addAttribute("menuTree", menuService.treeSelectVO(new MenuQuery().setFlag(ADMIN_FLAG)));
        return "system/role/au";
    }

    @RequiresPermissions("system:role:detail")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        RoleVO roleVO = roleService.getVO(new RoleQuery().setId(id));
        super.detail(model, roleVO);

        roleVO.setStateStr(dictService.getLabel(SYSTEM_ROLE_STATE, roleVO.getState()))
                .setMenus(Tree.term(roleVO.getMenus(), IMenuService.ROOT_ID));
        return "system/role/detail";
    }

    @RequiresPermissions("system:role:view")
    @GetMapping
    public String view(Model model) {
        super.list(model);
        model.addAttribute("stateMap", dictService.mapDataVO(SYSTEM_ROLE_STATE, DICT_NORM_TYPE));
        return "system/role/index";
    }
}
