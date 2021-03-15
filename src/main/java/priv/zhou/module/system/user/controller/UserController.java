package priv.zhou.module.system.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.shiro.filter.SyncOnlineFilter;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.domain.vo.RoleVO;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.vo.UserVO;
import priv.zhou.module.system.user.service.IUserService;

import java.util.stream.Collectors;

import static priv.zhou.common.constant.DictConst.SYSTEM_USER_STATE;

/**
 * 用户 视图控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    private final IUserService userService;

    private final IRoleService roleService;

    private final SyncOnlineFilter syncOnlineFilter;

    public UserController(IUserService userService, IRoleService roleService, SyncOnlineFilter syncOnlineFilter) {

        super("用户", "system:user");
        this.userService = userService;
        this.roleService = roleService;
        this.syncOnlineFilter = syncOnlineFilter;
    }

    @GetMapping("/login")
    public String login() {
        return "system/user/login";
    }


    @RequiresPermissions("system:user:add")
    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new UserVO().setState(0).setRoleIdSet(Sets.newHashSet()));
        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_USER_STATE, DICT_NORM_TYPE));
        model.addAttribute("roleList", roleService.listSelectVO(new RoleQuery().setState(0)));
        return "system/user/au";
    }

    @RequiresPermissions("system:user:update")
    @GetMapping("/update/{username}")
    public String update(Model model, @PathVariable String username) {
        UserVO userVO = userService.getVO(new UserQuery().setUsername(username));
        super.update(model, userVO);
        userVO.setRoleIdSet(userVO.getRoles()
                .stream()
                .map(RoleVO::getId)
                .collect(Collectors.toSet()))
                .setRoles(null);
        model.addAttribute("stateList", dictService.listDataVO(SYSTEM_USER_STATE, DICT_NORM_TYPE));
        model.addAttribute("roleList", roleService.listSelectVO(new RoleQuery().setState(0)));
        return "system/user/au";
    }

    @RequiresPermissions("system:user:reset:pwd")
    @GetMapping("/reset/pwd/{username}")
    public String resetPwd(Model model, @PathVariable String username) {
        UserVO userVO = userService.getVO(new UserQuery().setUsername(username));
        super.update(model, userVO);
        model.addAttribute(ACTION_KEY, "/rest/reset/pwd/" + userVO.getId());
        return "system/user/resetPwd";
    }


    @GetMapping("/profile")
    public String profile(Model model) {
        UserVO userVO = userService.getVO(new UserQuery().setUsername(ShiroUtil.getUserName()));
        super.detail(model, userVO);
        userVO.setStateStr(dictService.getLabel(SYSTEM_USER_STATE, userVO.getState()));
        return "system/user/profile";
    }

    @RequiresPermissions("system:user:detail")
    @GetMapping("/detail/{username}")
    public String detail(Model model, @PathVariable String username) {
        UserVO userVO = userService.getVO(new UserQuery().setUsername(username));
        super.detail(model, userVO);
        userVO.setStateStr(dictService.getLabel(SYSTEM_USER_STATE, userVO.getState()));
        return "system/user/detail";
    }

    @RequiresPermissions("system:user:view")
    @GetMapping
    public String view(Model model) {
        super.list(model);

        model.addAttribute("roleList", roleService.listSelectVO(new RoleQuery()));
        model.addAttribute("stateMap", dictService.mapDataVO(SYSTEM_USER_STATE));
        return "system/user/index";
    }
}
