package priv.zhou.module.system.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.ParseUtil;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.service.IUserService;

import javax.validation.Valid;

/**
 * 用户rest 控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@RestController
@RequestMapping("/system/user/rest")
public class UserRestController {

    private final IUserService userService;

    public UserRestController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public OutVO<NULL> login(UserDTO userDTO) {
        try {
//            SecurityUtils.getSubject().login(new UsernamePasswordToken("zhou", "123456"));
            SecurityUtils.getSubject().login(new UsernamePasswordToken(userDTO.getUsername(), userDTO.getPassword(), ParseUtil.unBox(userDTO.getRememberMe())));
        } catch (UnknownAccountException e) {
            return OutVO.fail(OutVOEnum.NONE_USERNAME);
        } catch (LockedAccountException e) {
            return OutVO.fail(OutVOEnum.LOCKED_USERNAME);
        } catch (AuthenticationException e) {
            return OutVO.fail(OutVOEnum.FAIL_LOGIN);
        }
        return OutVO.success();
    }

    @RequiresPermissions("system:user:add")
    @RequestMapping("/save")
    public OutVO<NULL> save(@Valid UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @RequiresPermissions("system:user:remove")
    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return userService.remove(new UserDTO().setId(id));
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping("/update")
    public OutVO<NULL> update(@Valid UserDTO userDTO) {
        return userService.update(userDTO);
    }

    @RequiresPermissions("system:user:list")
    @RequestMapping("/list")
    public OutVO<ListVO<UserDTO>> list(UserDTO userDTO, Page page) {
        return userService.list(userDTO, page);
    }

}
