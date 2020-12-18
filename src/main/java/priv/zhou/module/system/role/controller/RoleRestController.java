package priv.zhou.module.system.role.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.service.IRoleService;

import javax.validation.Valid;

/**
 * 角色 接口控制层
 *
 * @author zhou
 * @since 2020.04.15
 */
@RestController
@RequestMapping("/system/role/rest")
public class RoleRestController {

    private final IRoleService roleService;

    public RoleRestController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @RequiresPermissions("system:role:add")
    @RequestMapping("/save")
    public OutVO<NULL> save(@Valid RoleDTO roleDTO) {
        return roleService.save(roleDTO);
    }

    @RequiresPermissions("system:role:remove")
    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return roleService.remove(new RoleDTO().setId(id));
    }

    @RequiresPermissions("system:role:update")
    @RequestMapping("/update")
    public OutVO<NULL> update(@Valid RoleDTO roleDTO) {
        return roleService.update(roleDTO);
    }


    @RequiresPermissions("system:role:list")
    @RequestMapping("/list")
    public OutVO<ListVO<RoleDTO>> list(RoleDTO roleDTO, Page page) {
        return roleService.list(roleDTO, page);
    }


}
