package priv.zhou.module;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;

import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;


/**
 * 基础controller
 *
 * @author zhou
 * @since 2020.03.20
 */
@Controller
public class IndexController extends BaseController {

    private final IMenuService menuService;

    public IndexController(IMenuService menuService) {
        super(new Module());
        this.menuService = menuService;
    }

    @RequestMapping("/index")
    public String index(Model model) {

        UserPrincipal userPrincipal = ShiroUtil.getUser();
        model.addAttribute("user", userPrincipal);
        model.addAttribute("menuList", menuService.treeVO(new MenuQuery()
                .setUserId(userPrincipal.getId())
                .setFlag(ADMIN_FLAG)
                .setState(0)
                .setTypes(Lists.newArrayList(0, 1))));
        return "index";
    }

    @RequestMapping("/home")
    public String main() {
        return "home";
    }


}
