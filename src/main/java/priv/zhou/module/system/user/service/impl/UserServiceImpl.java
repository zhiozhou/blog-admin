package priv.zhou.module.system.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.Md5Util;
import priv.zhou.common.tools.RandomUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.framework.shiro.UserCredentialsMatcher;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.po.UserPO;
import priv.zhou.module.system.user.service.IUserService;

import java.util.List;


/**
 * 用户 服务层实现
 *
 * @author zhou
 * @since 2020.04.20
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements IUserService {

    private final UserDAO userDAO;

    private final UserCredentialsMatcher certMatcher;


    @Override
    public Result<NULL> save(UserDTO userDTO) {

        // 1.验证参数
        if (StringUtils.isBlank(userDTO.getPassword())) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        } else if (userDAO.count(new UserDTO().setUsername(userDTO.getUsername())) > 0) {
            return Result.fail(ResultEnum.REPEAT_KEY);
        }


        // 2.转换类型
        UserPO userPO = userDTO.toPO()
                .setState(0)
                .setSalt(RandomUtil.chars(6));

        // 3.密码加密
        userPO.setPassword(certMatcher.buildCert(Md5Util.encrypt(userPO.getPassword()), userPO.getSalt()));

        // 4.保存用户
        if (userDAO.save(userPO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        }

        // 5.保存角色
        userDAO.saveRole(userPO);
        return Result.success();

    }

    @Override
    public Result<NULL> remove(UserDTO userDTO) {
        if (null == userDTO.getId()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }

        userDAO.update(new UserPO().setId(userDTO.getId()).setState(12));
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> update(UserDTO userDTO) {

        UserPO userPO = userDTO.toPO();

        if (userDAO.count(new UserDTO().setUsername(userPO.getUsername()).setExclId(userPO.getId())) > 0) {
            return Result.fail(ResultEnum.REPEAT_KEY);
        } else if (userDAO.update(userPO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        } else if (userDAO.removeRole(userPO) < 1 || userDAO.saveRole(userPO) < 1) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        }
        return Result.success();
    }

    @Override
    public Result<NULL> updateState(UserDTO userDTO) {
        if (null == userDTO.getId() || null == userDTO.getState()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }
        return userDAO.update(userDTO.toPO()) > 0 ? Result.success() : Result.fail(ResultEnum.FAIL_OPERATION);
    }


    @Override
    public Result<NULL> resetPwd(UserDTO userDTO) {

        if (null == userDTO.getId() || StringUtils.isBlank(userDTO.getPassword())) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }

        UserPO userPO = userDTO.toPO(),
                userDB = userDAO.get(new UserDTO().setId(userPO.getId()));
        if (null == userDB) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        userPO.setPassword(certMatcher.buildCert(Md5Util.encrypt(userPO.getPassword()), userDB.getSalt()));
        ShiroUtil.getUserRealm().clearAllCachedAuthenticationInfo();
        return userDAO.update(userPO) > 0 ? Result.success() : Result.fail(ResultEnum.FAIL_OPERATION);
    }


    @Override
    public Result<UserDTO> get(UserDTO userDTO) {
        UserPO userPO = userDAO.get(userDTO);
        if (null == userPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }
        return Result.success(new UserDTO(userPO));
    }

    @Override
    public Result<List<UserDTO>> list(UserDTO userDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(userDAO.list(userDTO), UserDTO::new));
    }
}
