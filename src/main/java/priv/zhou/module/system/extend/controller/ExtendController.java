package priv.zhou.module.system.extend.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.domain.Module;
import priv.zhou.common.controller.BaseController;

/**
 * 模块生成 视图控制层
 *
 * @author zhou
 * @since 2020.04.15
 */
@Component
@RequestMapping("/system/extend")
public class ExtendController extends BaseController {

    private final Module module = new Module("模块", "system:extend");

    @RequiresPermissions("system:extend:index")
    @RequestMapping("/index")
    public String index(Model model) {
        super.list(model, module);

        return "system/extend/index";
    }
}
