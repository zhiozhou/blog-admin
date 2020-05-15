package priv.zhou.module.system.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单 接口控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@RestController
@RequestMapping("/system/menu/rest")
public class MenuRestController {

    private final IMenuService menuService;

    public MenuRestController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @RequiresPermissions("system:menu:add")
    @RequestMapping("/save")
    public OutVO<NULL> save(@Valid MenuDTO menuDTO) {
        return menuService.save(menuDTO);
    }

    @RequiresPermissions("system:menu:remove")
    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return menuService.remove(new MenuDTO().setId(id));
    }

    @RequiresPermissions("system:menu:update")
    @RequestMapping("/update")
    public OutVO<NULL> update(@Valid MenuDTO menuDTO) {
        return menuService.update(menuDTO);
    }

    @RequiresPermissions("system:menu:list")
    @RequestMapping("/list")
    public OutVO<List<MenuDTO>> list(MenuDTO menuDTO) {
        return menuService.list(menuDTO);
    }

    @RequiresPermissions("system:menu:list")
    @RequestMapping("/tree")
    public OutVO<List<MenuDTO>> trimList(MenuDTO menuDTO) {
        OutVO<List<MenuDTO>> outVO = menuService.list(menuDTO);
        return outVO.setData(IMenuService.toTree(outVO.getData()));
    }

}
