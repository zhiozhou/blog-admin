package priv.zhou.module;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.subject.Subject;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;

import static priv.zhou.module.system.menu.service.IMenuService.ADMIN_FLAG;
import static priv.zhou.module.system.role.service.IRoleService.ROOT_KEY;


/**
 * 基础controller
 *
 * @author zhou
 * @since 2020.03.20
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final IMenuService menuService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        Subject subject = ShiroUtil.getSubject();
        UserPrincipal userPrincipal = (UserPrincipal) subject.getPrincipal();
        model.addAttribute("user", userPrincipal);
        model.addAttribute("menuList", menuService.treeVO(new MenuQuery(ADMIN_FLAG)
                .setState(0)
                .setInTypes(Lists.newArrayList(0, 1))
                .setUserId(subject.hasRole(ROOT_KEY) ? null : userPrincipal.getId())));
        return "index";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }


}
