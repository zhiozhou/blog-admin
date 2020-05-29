package priv.zhou.module.blog.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;

import static priv.zhou.common.param.CONSTANT.SYSTEM_MENU_STATE;

/**
 * 菜单 视图控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@Component("blogMenuController")
@RequestMapping("/blog/menu")
public class MenuController extends BaseController {

    private final IMenuService menuService;

    public final static Integer FLAG = 2;

    private final String TYPE_KEY = "blog_menu_type";

    private final Module module = new Module("菜单", "blog:menu");

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @RequiresPermissions("blog:menu:add")
    @RequestMapping("/add")
    public String add(Model model) {
        fillAdd(model, new MenuDTO().setParentId(0).setType(0).setState(0));
        model.addAttribute("menuTree", IMenuService.toTree(menuService.list(new MenuDTO().setFlag(FLAG)).getData()));
        model.addAttribute("typeList", dictService.dataList(new DictDTO().setKey(TYPE_KEY)).getData());
        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(SYSTEM_MENU_STATE)).getData());
        return "blog/menu/au";
    }

    @RequiresPermissions("blog:menu:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        OutVO<MenuDTO> dtoVO = menuService.get(new MenuDTO().setId(id).setFlag(FLAG));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillUpdate(model, dtoVO.getData());

        model.addAttribute("menuTree", IMenuService.toTree(menuService.list(new MenuDTO().setFlag(FLAG)).getData()));
        model.addAttribute("typeList", dictService.dataList(new DictDTO().setKey(TYPE_KEY)).getData());
        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(SYSTEM_MENU_STATE)).getData());
        return "blog/menu/au";
    }

    @RequiresPermissions("blog:menu:list")
    @RequestMapping("/list")
    public String login(Model model) {
        fillList(model, module);

        model.addAttribute("typeMap", dictService.dataMap(new DictDTO().setKey(TYPE_KEY)).getData());
        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(SYSTEM_MENU_STATE)).getData());
        return "blog/menu/list";
    }
}
