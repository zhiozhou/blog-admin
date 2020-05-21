package priv.zhou.module.system.monitor.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.service.IRoleService;

/**
 * 监控 视图控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@Component
@RequestMapping("/system/monitor")
public class MonitorController extends BaseController {

    private final IRoleService roleService;

    private final Module module = new Module("监控", "system:monitor");

    public MonitorController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @RequiresPermissions("system:monitor:list")
    @RequestMapping("/list")
    public String login(Model model) {
        fillList(model, module);

        model.addAttribute("id", ShiroUtil.getSession().getId());
        model.addAttribute("roleList", roleService.list(new RoleDTO(), new Page(0)).getData().getList());
        return "system/monitor/list";
    }
}
