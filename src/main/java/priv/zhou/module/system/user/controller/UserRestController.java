package priv.zhou.module.system.user.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.constant.Save;
import priv.zhou.common.constant.Update;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.dto.UserLoginDTO;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.vo.UserTableVO;
import priv.zhou.module.system.user.service.IUserService;

import javax.validation.constraints.NotEmpty;

/**
 * 用户rest 控制层
 *
 * @author zhou
 * @since 2020.03.20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user/rest")
public class UserRestController {

    private final IUserService userService;

    @PostMapping("/login")
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
    @PostMapping("/save")
    public Result<NULL> save(@Validated({Save.class}) UserDTO saveDTO) {
        return userService.save(saveDTO);
    }

    @RequiresPermissions("system:user:remove")
    @PostMapping("/remove")
    public Result<NULL> remove(@RequestParam(value = "ids[]") Integer[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }
        return userService.remove(ids);
    }

    @RequiresPermissions("system:user:update")
    @PostMapping("/update")
    public Result<NULL> update(@Validated({Update.class}) UserDTO updateDTO) {
        return userService.update(updateDTO);
    }

    @RequiresPermissions("system:user:reset:pwd")
    @PostMapping("/reset/pwd/{id}")
    public Result<NULL> resetPwd(@PathVariable Integer id, @NotEmpty(message = "密码不可为空") String password) {
        return userService.resetPwd(id, password);
    }

    @PostMapping("/reset/pwd/own")
    public Result<NULL> resetPwd(@NotEmpty(message = "密码不可为空") String password) {
        return userService.resetPwd(ShiroUtil.getUserId(), password);
    }

    @RequiresPermissions("system:user:freeze")
    @PostMapping("/freeze/{id}")
    public Result<NULL> freeze(@PathVariable Integer id) {
        return userService.freeze(id);
    }

    @RequiresPermissions("system:user:freeze")
    @PostMapping("/unfreeze/{id}")
    public Result<NULL> unfreeze(@PathVariable Integer id) {
        return userService.unfreeze(id);
    }


    @RequiresPermissions("system:user:view")
    @PostMapping("/list")
    public Result<TableVO<UserTableVO>> list(UserQuery query, Page page) {
        return Result.table(userService.listTableVO(query, page));
    }

}
