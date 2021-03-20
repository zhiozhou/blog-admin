package priv.zhou.module.system.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.RandomUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.RestException;
import priv.zhou.framework.shiro.UserCredentialsMatcher;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.user.domain.bo.UserBO;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.dao.UserRoleDAO;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.po.UserPO;
import priv.zhou.module.system.user.domain.po.UserRolePO;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.vo.UserTableVO;
import priv.zhou.module.system.user.domain.vo.UserVO;
import priv.zhou.module.system.user.service.IUserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    private final UserRoleDAO userRoleDAO;

    private final UserCredentialsMatcher certMatcher;


    @Override
    public Result<NULL> save(UserDTO userDTO) {

        if (userDAO.count(new UserQuery().setUsername(userDTO.getUsername())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (userDAO.count(new UserQuery().setPhone(userDTO.getPhone())) > 0) {
            return Result.fail(ResultEnum.EXIST_PHONE);
        }

        UserPO userPO = new UserPO()
                .setUsername(userDTO.getUsername())
                .setSalt(RandomUtil.chars(6))
                .setName(userDTO.getName())
                .setPhone(userDTO.getPhone())
                .setState(0)
                .setCreateBy(ShiroUtil.getUserId());
        userPO.setPassword(certMatcher.buildCert(userDTO.getPassword(), userPO.getSalt()));

        if (userDAO.save(userPO) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (userRoleDAO.saveList(userDTO.getRoles()
                .stream()
                .map(roleId -> new UserRolePO()
                        .setRoleId(roleId)
                        .setUserId(userPO.getId()))
                .collect(Collectors.toList())) != userDTO.getRoles().size()) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }
        return Result.success();

    }

    @Override
    @Transactional
    public Result<NULL> remove(List<String> usernames) {
        for (String username : usernames) {
            UserPO userPO = userDAO.get(new UserQuery().setUsername(username));
            if (null == userPO) {
                return Result.fail(ResultEnum.EMPTY_DATA);
            } else if (userDAO.remove(userPO.getId()) < 1) {
                throw new RestException(ResultEnum.LATER_RETRY);
            } else if (userRoleDAO.remove(userPO.getId()) < 1) {
                throw new RestException(ResultEnum.LATER_RETRY);
            }
            ShiroUtil.clearUserAuthorization(username);
        }
        return Result.fail(ResultEnum.SUCCESS);
    }

    @Override
    @Transactional
    public Result<NULL> update(UserDTO userDTO) {
        if (userDAO.count(new UserQuery().setPhone(userDTO.getPhone()).setRidId(userDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_PHONE);
        }

        UserBO userDB = userDAO.getBO(new UserQuery().setId(userDTO.getId()));
        if (null == userDB) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        } else if (userDAO.update(new UserPO()
                .setId(userDTO.getId())
                .setName(userDTO.getName())
                .setPhone(userDTO.getPhone())
                .setModifiedBy(ShiroUtil.getUserId())) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }

        Set<Integer> roleSet = userDB.getRoles()
                .stream()
                .map(RolePO::getId)
                .collect(Collectors.toSet());
        if (roleSet.size() != userDTO.getRoles().size() ||
                !roleSet.containsAll(userDTO.getRoles())) {
            if (userRoleDAO.delete(userDTO.getId()) < 1) {
                return Result.fail(ResultEnum.LATER_RETRY);
            } else if (userRoleDAO.saveList(userDTO.getRoles()
                    .stream()
                    .map(roleId -> new UserRolePO()
                            .setRoleId(roleId)
                            .setUserId(userDTO.getId()))
                    .collect(Collectors.toList())) != userDTO.getRoles().size()) {
                return Result.fail(ResultEnum.LATER_RETRY);
            }
            ShiroUtil.clearUserAuthorization(userDB.getUsername());
        }


        return Result.success();
    }

    @Override
    public UserVO getVO(UserQuery query) {
        return userDAO.getVO(query);
    }

    @Override
    public List<UserTableVO> listTableVO(UserQuery query, Page page) {
        startPage(page);
        return userDAO.listTableVO(query);
    }

    @Override
    public Result<NULL> freeze(String username) {
        UserPO userPO = userDAO.get(new UserQuery().setUsername(username));
        if (null == userPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }
        return userDAO.update(new UserPO().setId(userPO.getId()).setState(11).setModifiedBy(ShiroUtil.getUserId())) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.LATER_RETRY);
    }

    @Override
    public Result<NULL> unfreeze(String username) {
        UserPO userPO = userDAO.get(new UserQuery().setUsername(username));
        if (null == userPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }
        return userDAO.update(new UserPO().setId(userPO.getId()).setState(0).setModifiedBy(ShiroUtil.getUserId())) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.LATER_RETRY);
    }


    @Override
    public Result<NULL> resetPwd(Integer id, String password) {
        UserPO userPO = userDAO.get(new UserQuery().setId(id));
        if (null == userPO) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        userPO.setPassword(certMatcher.buildCert(password, userPO.getSalt()));
        if (userDAO.update(userPO) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }
        ShiroUtil.stopSession();
        ShiroUtil.clearAuthentication(userPO.getUsername());
        return Result.success();
    }

}
