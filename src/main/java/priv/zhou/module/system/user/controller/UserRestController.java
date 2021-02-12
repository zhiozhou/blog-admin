package priv.zhou.module.system.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.user.domain.dto.UserLoginDTO;
import priv.zhou.module.system.user.domain.dto.UserSaveDTO;
import priv.zhou.module.system.user.domain.dto.UserUpdateDTO;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.vo.UserTableVO;
import priv.zhou.module.system.user.service.IUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
    public Result<NULL> login(UserLoginDTO loginDTO) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.isRememberMe()));
        } catch (UnknownAccountException e) {
            return Result.fail(ResultEnum.NONE_USERNAME);
        } catch (LockedAccountException e) {
            return Result.fail(ResultEnum.LOCKED_USERNAME);
        } catch (DisabledAccountException e) {
            return Result.fail(ResultEnum.FROZE_USERNAME);
        } catch (AuthenticationException e) {
            return Result.fail(ResultEnum.FAIL_LOGIN);
        }
        return Result.success();
    }


    @RequiresPermissions("system:user:add")
    @RequestMapping("/save")
    public Result<NULL> save(@Valid UserSaveDTO saveDTO) {
        return userService.save(saveDTO);
    }

    @RequiresPermissions("system:user:remove")
    @RequestMapping("/remove")
    public Result<NULL> remove(@RequestParam(value = "ids[]") int[] ids) {
        return userService.remove(ids);
    }

    @RequiresPermissions("system:user:update")
    @RequestMapping("/update/{id}")
    public Result<NULL> update(@PathVariable Integer id, @Valid UserUpdateDTO updateDTO) {
        return userService.update(updateDTO.setId(id));
    }

    @RequiresPermissions("system:user:reset:pwd")
    @RequestMapping("/reset/pwd/{id}")
    public Result<NULL> resetPwd(@PathVariable Integer id, @NotEmpty(message = "密码不可为空") String password) {
        return userService.resetPwd(id, password);
    }

    @RequestMapping("/reset/pwd/own")
    public Result<NULL> resetPwd(@NotEmpty(message = "密码不可为空") String password) {
        return userService.resetPwd(ShiroUtil.getUserId(), password);
    }


    @RequiresPermissions("system:user:freeze")
    @RequestMapping("/freeze/{id}")
    public Result<NULL> freeze(@PathVariable Integer id) {
        return userService.freeze(id);
    }

    @RequiresPermissions("system:user:freeze")
    @RequestMapping("/unfreeze/{id}")
    public Result<NULL> unfreeze(@PathVariable Integer id) {
        return userService.unfreeze(id);
    }


    @RequiresPermissions("system:user:list")
    @RequestMapping("/list")
    public Result<TableVO<UserTableVO>> list(UserQuery query, Page page) {
        // TODO 角色多选有问题
        return Result.success(TableVO.build(userService.listTableVO(query, page)));
    }

}
