package priv.zhou.module.system.role.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.tools.PinyinUtil;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.domain.vo.RoleTableVO;
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
    public Result<NULL> save(@Valid RoleDTO roleDTO) {
        if (StringUtils.isBlank(roleDTO.getKey())) {
            roleDTO.setKey(PinyinUtil.toPinyin(roleDTO.getName()));
        }
        return roleService.save(roleDTO);
    }

    @RequiresPermissions("system:role:remove")
    @RequestMapping("/remove/")
    public Result<NULL> remove(Integer[] ids) {
        return roleService.remove(ids);
    }

    @RequiresPermissions("system:role:update")
    @RequestMapping("/update/{id}")
    public Result<NULL> update(@PathVariable Integer id, @Valid RoleDTO roleDTO) {
        return roleService.update(roleDTO.setId(id));
    }


    @RequiresPermissions("system:role:list")
    @RequestMapping("/list")
    public Result<TableVO<RoleTableVO>> list(RoleQuery query, Page page) {
        return Result.table(roleService.listTableVO(query, page));
    }


}
