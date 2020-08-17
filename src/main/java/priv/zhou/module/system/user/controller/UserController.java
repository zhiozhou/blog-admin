package priv.zhou.module.system.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.service.IUserService;

import static priv.zhou.common.param.CONSTANT.*;

/**
 * 用户 视图控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@Component
@RequestMapping("/system/user")
public class UserController extends BaseController {

    private final IUserService userService;

    private final IRoleService roleService;

    private final Module module = new Module("用户", "system:user");

    public UserController(IUserService userService, IRoleService roleService) {
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
        supplyAdd(model, new UserDTO().setState(0));

        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(SYSTEM_USER_STATE)).getData());
        model.addAttribute("roleList", roleService.list(new RoleDTO().setState(0), new Page(0)).getData().getList());
        return "system/user/au";
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        OutVO<UserDTO> dtoVO = userService.get(new UserDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        supplyUpdate(model, dtoVO.getData());

        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(SYSTEM_USER_STATE)).getData());
        model.addAttribute("roleList", roleService.list(new RoleDTO().setState(0), new Page(0)).getData().getList());
        return "system/user/au";
    }

    @RequiresPermissions("system:user:resetPwd")
    @RequestMapping("/resetPwd/{id}")
    public String resetPwd(Model model, @PathVariable Integer id) {
        OutVO<UserDTO> dtoVO = userService.get(new UserDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        supplyUpdate(model, dtoVO.getData());
        return "system/user/resetPwd";
    }


    @RequiresPermissions("system:user:detail")
    @RequestMapping("/detail/{id}")
    public String read(Model model, @PathVariable Integer id) {
        OutVO<UserDTO> dtoVO = userService.get(new UserDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        UserDTO userDTO = dtoVO.getData();
        userDTO.setStateStr(dictService.getData(new DictDTO().setKey(SYSTEM_USER_STATE).setCode(userDTO.getState())).getData().getLabel());
        supplyDetail(model, userDTO);
        return "system/user/detail";
    }

    @RequiresPermissions("system:user:list")
    @RequestMapping("/list")
    public String list(Model model) {
        supplyList(model, module);

        model.addAttribute("roleList", roleService.list(new RoleDTO(), new Page(0)).getData().getList());
        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(SYSTEM_USER_STATE), false).getData());
        return "system/user/list";
    }
}
