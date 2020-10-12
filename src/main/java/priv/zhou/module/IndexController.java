package priv.zhou.module;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.menu.controller.MenuController;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.service.IMenuService;
import priv.zhou.module.system.user.domain.dto.UserDTO;

import java.util.List;

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

        UserDTO userDTO = ShiroUtil.getUser();
        OutVO<List<MenuDTO>> listRes = menuService.list(
                new MenuDTO()
                        .setUserId(userDTO.getId())
                        .setFlag(ADMIN_FLAG)
                        .setState(0)
                        .setTypes(Lists.newArrayList(0, 1))
        );

        model.addAttribute("user", userDTO);
        model.addAttribute("menuList", IMenuService.toTree(listRes.getData()));
        return "index";
    }

    @RequestMapping("/home")
    public String main() {
        return "home";
    }


}
