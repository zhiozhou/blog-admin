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
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutEnum;
import priv.zhou.common.tools.ParseUtil;
import priv.zhou.common.tools.RsaUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.service.IUserService;

import javax.validation.Valid;

import static priv.zhou.common.misc.Const.RSA_PRIVATE_KEY;

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
    public Result<NULL> login(UserDTO userDTO) {
        try {
            // 参数传入时需要将+号替换为%2B
            userDTO.setPassword(RsaUtil.decode(userDTO.getPassword(), RSA_PRIVATE_KEY));
        } catch (Exception e) {
            return Result.fail(OutEnum.FAIL_PARAM);
        }

        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(userDTO.getUsername(), userDTO.getPassword(), ParseUtil.unBox(userDTO.getRememberMe())));
        } catch (UnknownAccountException e) {
            return Result.fail(OutEnum.NONE_USERNAME);
        } catch (LockedAccountException e) {
            return Result.fail(OutEnum.LOCKED_USERNAME);
        } catch (AuthenticationException e) {
            return Result.fail(OutEnum.FAIL_LOGIN);
        }
        return Result.success();
    }

    @RequiresPermissions("system:user:add")
    @RequestMapping("/save")
    public Result<NULL> save(@Valid UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @RequiresPermissions("system:user:remove")
    @RequestMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return userService.remove(new UserDTO().setId(id));
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping("/update")
    public Result<NULL> update(@Valid UserDTO userDTO) {
        return userService.update(userDTO);
    }


    @RequiresPermissions("system:user:reset:pwd")
    @RequestMapping("/reset/pwd/{id}")
    public Result<NULL> resetPwd(@PathVariable Integer id, UserDTO userDTO) {
        return userService.resetPwd(userDTO.setId(id));
    }

    @RequestMapping("/reset/pwd/own")
    public Result<NULL> resetPwd(UserDTO userDTO) {
        return userService.resetPwd(userDTO.setId(ShiroUtil.getUserId()));
    }


    @RequiresPermissions("system:user:freeze")
    @RequestMapping("/freeze/{id}")
    public Result<NULL> freeze(@PathVariable Integer id) {
        return userService.updateState(new UserDTO().setId(id).setState(11));
    }

    @RequiresPermissions("system:user:freeze")
    @RequestMapping("/unfreeze/{id}")
    public Result<NULL> unfreeze(@PathVariable Integer id) {
        return userService.updateState(new UserDTO().setId(id).setState(0));
    }


    @RequiresPermissions("system:user:list")
    @RequestMapping("/list")
    public Result<TableVO<UserDTO>> list(UserDTO userDTO, Page page) {
        return Result.table(userService.list(userDTO, page));
    }

}
