package priv.zhou.module.system.role.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.Result;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.service.IRoleService;

import static priv.zhou.common.constant.DictConst.SYSTEM_ROLE_STATE;
import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;

/**
 * 角色 视图控制层
 *
 * @author zhou
 * @since 2020.04.15
 */
@Component
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    private final IRoleService roleService;

    private final IMenuService menuService;

    public RoleController(IRoleService roleService, IMenuService menuService) {
        super(new Module("角色", "system:role"));
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @RequiresPermissions("system:role:add")
    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new RoleDTO().setState(0));
        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_ROLE_STATE, DICT_NORM_TYPE));
        model.addAttribute("menuTree", menuService.tree(new MenuDTO().setFlag(ADMIN_FLAG)));
        return "system/role/au";
    }

    @RequiresPermissions("system:role:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        Result<RoleDTO> dtoVO = roleService.get(new RoleDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());

        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_ROLE_STATE, DICT_NORM_TYPE));
        model.addAttribute("menuTree", menuService.tree(new MenuDTO().setFlag(ADMIN_FLAG)));
        return "system/role/au";
    }

    @RequiresPermissions("system:role:detail")
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        Result<RoleDTO> dtoVO = roleService.get(new RoleDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        RoleDTO roleDTO = dtoVO.getData();
        roleDTO.setMenuList(IMenuService.toTree(roleDTO.getMenuList()))
                .setStateStr(dictService.getLabel(SYSTEM_ROLE_STATE, roleDTO.getState()));
        super.detail(model, roleDTO);
        return "system/role/detail";
    }

    @RequiresPermissions("system:role:list")
    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);

        model.addAttribute("stateMap", dictService.mapDataVO(SYSTEM_ROLE_STATE, DICT_NORM_TYPE));
        return "system/role/list";
    }
}
