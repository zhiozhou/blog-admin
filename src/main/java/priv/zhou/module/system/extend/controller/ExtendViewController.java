package priv.zhou.module.system.extend.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseViewController;

/**
 * 模块生成 视图控制层
 *
 * @author zhou
 * @since 2020.04.15
 */
@Controller
@RequestMapping("/system/extend")
public class ExtendViewController extends BaseViewController {

    public ExtendViewController(){
        super("模块", "system:extend");
    }

    @RequiresPermissions("system:extend:view")
    @GetMapping
    public String view(Model model) {
        super.list(model);
        return "system/extend/index";
    }
}
