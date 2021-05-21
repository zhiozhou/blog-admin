package priv.zhou.module.system.monitor.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseViewController;
import priv.zhou.common.properties.AppProperties;
import priv.zhou.common.tools.AesUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.service.IRoleService;

/**
 * 监控 视图控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@Controller
@RequestMapping("/system/monitor")
public class MonitorViewController extends BaseViewController {

    private final IRoleService roleService;

    private final AppProperties appProperties;

    public MonitorViewController(IRoleService roleService, AppProperties appProperties) {
        super("监控", "system:monitor");
        this.roleService = roleService;
        this.appProperties = appProperties;
    }

    @RequiresPermissions("system:monitor:view")
    @GetMapping
    public String view(Model model) {
        super.list(model);

        model.addAttribute("id", AesUtil.encrypt((String) ShiroUtil.getSessionId(), appProperties.getAesSeed()));
        model.addAttribute("roleList", roleService.listSelectVO(new RoleQuery()));
        return "system/monitor/index";
    }
}
