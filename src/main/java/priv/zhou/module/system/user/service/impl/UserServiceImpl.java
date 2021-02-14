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
import priv.zhou.framework.shiro.UserCredentialsMatcher;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.dto.UserSaveDTO;
import priv.zhou.module.system.user.domain.dto.UserUpdateDTO;
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

    private final RoleDAO roleDAO;

    private final UserCredentialsMatcher certMatcher;


    @Override
    public Result<NULL> save(UserSaveDTO saveDTO) {

        if (userDAO.count(new UserQuery().setUsername(saveDTO.getUsername())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (userDAO.count(new UserQuery().setPhone(saveDTO.getPhone())) > 0) {
            return Result.fail(ResultEnum.EXIST_PHONE);
        }

        UserPO userPO = new UserPO()
                .setUsername(saveDTO.getUsername())
                .setSalt(RandomUtil.chars(6))
                .setName(saveDTO.getName())
                .setPhone(saveDTO.getPhone())
                .setState(0)
                .setCreateBy(ShiroUtil.getUserId());
        userPO.setPassword(certMatcher.buildCert(saveDTO.getPassword(), userPO.getSalt()));

        if (userDAO.save(userPO) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (userDAO.relateRole(saveDTO.getRoles()
                .stream()
                .map(roleId -> new UserRolePO()
                        .setRoleId(roleId)
                        .setUserId(userPO.getId()))
                .collect(Collectors.toList())) != saveDTO.getRoles().size()) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }
        return Result.success();

    }

    @Override
    public Result<NULL> remove(int[] ids) {
        for (int id : ids) {
            if (userDAO.remove(new UserQuery().setId(id)) < 1) {
                return Result.fail(ResultEnum.FAIL_PARAM);
            }
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> update(UserUpdateDTO updateDTO) {
        if (userDAO.count(new UserQuery().setPhone(updateDTO.getPhone()).setRidId(updateDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_PHONE);
        }

        UserPO userPO = userDAO.get(new UserQuery().setId(updateDTO.getId()));
        if (null == userPO) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        } else if (userDAO.update(new UserPO()
                .setId(updateDTO.getId())
                .setName(updateDTO.getName())
                .setPhone(updateDTO.getPhone())
                .setModifiedBy(ShiroUtil.getUserId())) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        }
        Set<Integer> roleSet = roleDAO.idSet(updateDTO.getId());
        if (roleSet.size() != updateDTO.getRoles().size() ||
                updateDTO.getRoles().stream()
                        .noneMatch(roleSet::contains)) {
            if (userDAO.unRelateRole(updateDTO.getId()) < 1) {
                return Result.fail(ResultEnum.LATER_RETRY);
            } else if (userDAO.relateRole(updateDTO.getRoles()
                    .stream()
                    .map(roleId -> new UserRolePO()
                            .setRoleId(roleId)
                            .setUserId(updateDTO.getId()))
                    .collect(Collectors.toList())) != updateDTO.getRoles().size()) {
                return Result.fail(ResultEnum.LATER_RETRY);
            }
            ShiroUtil.clearUserAuthorization(userPO.getUsername());
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
    public Result<NULL> freeze(Integer id) {
        UserPO userPO = userDAO.get(new UserQuery().setId(id));
        if (null == userPO) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        return userDAO.update(new UserPO().setId(id).setState(11).setModifiedBy(ShiroUtil.getUserId())) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.FAIL_OPERATION);
    }

    @Override
    public Result<NULL> unfreeze(Integer id) {
        UserPO userPO = userDAO.get(new UserQuery().setId(id));
        if (null == userPO) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        return userDAO.update(new UserPO().setId(id).setState(0).setModifiedBy(ShiroUtil.getUserId())) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.FAIL_OPERATION);
    }


    @Override
    public Result<NULL> resetPwd(Integer id, String password) {
        UserPO userPO = userDAO.get(new UserQuery().setId(id));
        if (null == userPO) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }
        userPO.setPassword(certMatcher.buildCert(password, userPO.getSalt()));
        if (userDAO.update(userPO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        }
        ShiroUtil.stopSession();
        ShiroUtil.clearAuthentication(userPO.getUsername());
        return Result.success();
    }

}
