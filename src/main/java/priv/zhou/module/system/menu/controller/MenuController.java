package priv.zhou.module.system.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.service.IMenuService;

import static priv.zhou.common.constant.DictConst.SYSTEM_MENU_STATE;
import static priv.zhou.common.constant.DictConst.SYSTEM_MENU_TYPE;
import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;

/**
 * 菜单 视图控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    public MenuController(IMenuService menuService) {
        super(new Module("菜单", "system:menu"));
        this.menuService = menuService;
    }

    @RequiresPermissions("system:menu:add")
    @RequestMapping("/add")
    public String add(Model model) {

        super.add(model, new MenuDTO().setParentId(0).setType(0).setState(0));
        model.addAttribute("typeList", dictService.listDataVO(SYSTEM_MENU_TYPE, DICT_NORM_TYPE));
        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_MENU_STATE, DICT_NORM_TYPE));
        model.addAttribute("menuTree", menuService.tree(new MenuDTO().setFlag(ADMIN_FLAG)));
        return "system/menu/au";
    }

    @RequiresPermissions("system:menu:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        super.update(model, menuService.getVO(new MenuQuery().setId(id).setFlag(ADMIN_FLAG)));

        model.addAttribute("typeList", dictService.listDataVO(SYSTEM_MENU_TYPE, DICT_NORM_TYPE));
        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_MENU_STATE, DICT_NORM_TYPE));
        model.addAttribute("menuTree", menuService.tree(new MenuDTO().setFlag(ADMIN_FLAG)));
        return "system/menu/au";
    }

    @RequiresPermissions("system:menu:list")
    @RequestMapping("/list")
    public String login(Model model) {
        super.list(model);

        model.addAttribute("typeMap", dictService.mapDataVO(SYSTEM_MENU_TYPE, DICT_NORM_TYPE));
        model.addAttribute("stateMap", dictService.mapDataVO(SYSTEM_MENU_STATE, DICT_NORM_TYPE));
        return "system/menu/list";
    }
}
