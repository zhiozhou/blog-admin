package priv.zhou.module.system.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.annotation.CipherId;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.service.IUserService;

import static priv.zhou.common.misc.Const.RSA_PUBLIC_KEY;
import static priv.zhou.common.misc.Const.SYSTEM_USER_STATE;

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

    public UserController(IUserService userService, IRoleService roleService) {
        super(new Module("用户", "system:user"));
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("rsaPublicKey", RSA_PUBLIC_KEY);
        return "system/user/login";
    }

    @RequiresPermissions("system:user:add")
    @RequestMapping("/add")
    public String add(Model model) {

        super.add(model, new UserDTO().setState(0));
        model.addAttribute("stateList", dictService.listData(SYSTEM_USER_STATE));
        model.addAttribute("roleList", roleService.list(new RoleDTO().setState(0)).getData().getList());
        return "system/user/au";
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        OutVO<UserDTO> dtoVO = userService.get(new UserDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());

        model.addAttribute("stateList", dictService.listData(SYSTEM_USER_STATE));
        model.addAttribute("roleList", roleService.list(new RoleDTO().setState(0)).getData().getList());
        return "system/user/au";
    }

    @RequestMapping("/reset/pwd/own")
    public String resetPwd(Model model) {
        resetPwd(model, ShiroUtil.getUserId());
        model.addAttribute(ACTION_KEY, "/rest/reset/pwd/own");
        return "system/user/resetPwd";
    }


    @RequiresPermissions("system:user:reset:pwd")
    @RequestMapping("/reset/pwd/{id}")
    public String resetPwd(Model model, @PathVariable Integer id) {
        OutVO<UserDTO> dtoVO = userService.get(new UserDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        UserDTO userDTO = dtoVO.getData();
        super.update(model, userDTO);
        model.addAttribute(ACTION_KEY, "/rest/reset/pwd/" + userDTO.getId());
        return "system/user/resetPwd";
    }


    @RequestMapping("/profile")
    public String self(Model model) {
        detail(model, ShiroUtil.getUserId());
        return "system/user/profile";
    }

    @RequiresPermissions("system:user:detail")
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable @CipherId Integer id) {
        OutVO<UserDTO> dtoVO = userService.get(new UserDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        UserDTO userDTO = dtoVO.getData();
        userDTO.setStateStr(dictService.getLabel(SYSTEM_USER_STATE, userDTO.getState()));
        super.detail(model, userDTO);
        return "system/user/detail";
    }

    @RequiresPermissions("system:user:list")
    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);

        model.addAttribute("roleList", roleService.list(new RoleDTO()).getData().getList());
        model.addAttribute("stateMap", dictService.mapData(SYSTEM_USER_STATE, true));
        return "system/user/list";
    }
}
