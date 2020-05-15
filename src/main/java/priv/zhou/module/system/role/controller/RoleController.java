package priv.zhou.module.system.role.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.service.IRoleService;

import static priv.zhou.common.param.CONSTANT.SYSTEM_ROLE_STATE;

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

    private final Module module = new Module("角色", "system:role");

    public RoleController(IRoleService roleService, IMenuService menuService) {
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @RequiresPermissions("system:role:add")
    @RequestMapping("/add")
    public String add(Model model) {
        fillAdd(model, new RoleDTO().setState(0));

        model.addAttribute("menuTree", IMenuService.toTree(menuService.list(new MenuDTO()).getData()));
        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(SYSTEM_ROLE_STATE)).getData());
        return "system/role/au";
    }

    @RequiresPermissions("system:role:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        OutVO<RoleDTO> dtoVO = roleService.get(new RoleDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillUpdate(model, dtoVO.getData());

        model.addAttribute("menuTree", IMenuService.toTree(menuService.list(new MenuDTO()).getData()));
        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(SYSTEM_ROLE_STATE)).getData());
        return "system/role/au";
    }

    @RequiresPermissions("system:role:detail")
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<RoleDTO> dtoVO = roleService.get(new RoleDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        RoleDTO roleDTO = dtoVO.getData();
        roleDTO.setMenuList(IMenuService.toTree(roleDTO.getMenuList()))
                .setStateStr(dictService.getData(new DictDTO().setKey(SYSTEM_ROLE_STATE).setCode(roleDTO.getState())).getData().getLabel());
        fillDetail(model, roleDTO);
        return "system/role/detail";
    }

    @RequiresPermissions("system:role:list")
    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);

        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(SYSTEM_ROLE_STATE)).getData());
        return "system/role/list";
    }
}
