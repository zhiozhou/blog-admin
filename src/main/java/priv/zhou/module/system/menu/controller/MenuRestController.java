package priv.zhou.module.system.menu.controller;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.constant.Update;
import priv.zhou.common.domain.Result;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.domain.vo.MenuTableVO;
import priv.zhou.module.system.menu.domain.vo.MenuVO;
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
@RequiredArgsConstructor
@RequestMapping("/system/menu/rest")
public class MenuRestController {

    private final IMenuService menuService;

    @RequiresPermissions("system:menu:add")
    @RequestMapping("/save")
    public Result<NULL> save(@Valid MenuDTO menuDTO) {
        return menuService.save(menuDTO.setFlag(ADMIN_FLAG));
    }

    @RequiresPermissions("system:menu:remove")
    @RequestMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return menuService.remove(id);
    }

    @RequiresPermissions("system:menu:update")
    @RequestMapping("/update")
    public Result<NULL> update(@Validated({Update.class}) MenuDTO menuDTO) {
        return menuService.update(menuDTO.setFlag(ADMIN_FLAG));
    }

    @RequiresPermissions("system:menu:view")
    @RequestMapping("/list")
    public Result<List<MenuVO>> trimList(MenuQuery query) {
        return Result.success(menuService.treeVO(query.setFlag(ADMIN_FLAG)));
    }
}
