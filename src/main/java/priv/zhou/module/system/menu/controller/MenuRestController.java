package priv.zhou.module.system.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;

import javax.validation.Valid;
import java.util.List;

import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;

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
    public Result<NULL> save(@Valid MenuDTO menuDTO) {
        return menuService.save(menuDTO.setFlag(ADMIN_FLAG));
    }

    @RequiresPermissions("system:menu:remove")
    @RequestMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return menuService.remove(new MenuDTO().setId(id).setFlag(ADMIN_FLAG));
    }

    @RequiresPermissions("system:menu:update")
    @RequestMapping("/update")
    public Result<NULL> update(@Valid MenuDTO menuDTO) {
        return menuService.update(menuDTO.setFlag(ADMIN_FLAG));
    }

    @RequiresPermissions("system:menu:list")
    @RequestMapping("/list")
    public Result<List<MenuDTO>> list(MenuDTO menuDTO) {
        return menuService.list(menuDTO.setFlag(ADMIN_FLAG));
    }

    @RequiresPermissions("system:menu:list")
    @RequestMapping("/tree")
    public Result<List<MenuDTO>> trimList(MenuDTO menuDTO) {
        return Result.success(menuService.tree(menuDTO.setFlag(ADMIN_FLAG)));
    }

    @RequiresPermissions("system:menu:refresh")
    @RequestMapping("/refresh")
    public Result<NULL> refresh() {
        ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        return Result.success();
    }

}
