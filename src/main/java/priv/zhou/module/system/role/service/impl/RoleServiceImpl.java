package priv.zhou.module.system.role.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.menu.domain.po.MenuPO;
import priv.zhou.module.system.role.domain.bo.RoleBO;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.role.domain.dao.RoleMenuDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RoleMenuPO;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.domain.vo.RoleSelectVO;
import priv.zhou.module.system.role.domain.vo.RoleTableVO;
import priv.zhou.module.system.role.domain.vo.RoleVO;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.dao.UserRoleDAO;
import priv.zhou.module.system.user.domain.query.UserRoleQuery;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseService implements IRoleService {

    private final RoleDAO roleDAO;

    private final RoleMenuDAO roleMenuDAO;

    private final UserRoleDAO userRoleDAO;


    @Override
    @Transactional
    public Result<NULL> save(RoleDTO roleDTO) {
        if (roleDAO.count(new RoleQuery().setName(roleDTO.getName())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleQuery().setKey(roleDTO.getKey())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        RolePO rolePO = new RolePO()
                .setKey(roleDTO.getKey())
                .setName(roleDTO.getName())
                .setRemark(roleDTO.getRemark())
                .setState(0)
                .setCreateBy(ShiroUtil.getUserId());
        if (roleDAO.save(rolePO) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (null != roleDTO.getMenus() && roleMenuDAO.saveList(roleDTO.getMenus()
                .stream()
                .map(menuId -> new RoleMenuPO()
                        .setRoleId(rolePO.getId())
                        .setMenuId(menuId))
                .collect(Collectors.toList())) != roleDTO.getMenus().size()) {
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> remove(Integer[] ids) {
        if (userRoleDAO.count(new UserRoleQuery().setRoleIds(ids)) > 0) {
            return Result.fail(ResultEnum.EXIST_RELATION, "角色下尚有用户，不可删除");
        } else if (roleDAO.removeList(ids) != ids.length) {
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> update(RoleDTO roleDTO) {

        if (roleDAO.count(new RoleQuery().setName(roleDTO.getName()).setRidId(roleDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleQuery().setKey(roleDTO.getKey()).setRidId(roleDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        RoleBO roleDB = roleDAO.getBO(new RoleQuery().setId(roleDTO.getId()));
        if (null == roleDB) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }

        RolePO rolePO = new RolePO()
                .setId(roleDTO.getId())
                .setKey(roleDTO.getKey())
                .setName(roleDTO.getName())
                .setRemark(roleDTO.getRemark())
                .setModifiedBy(ShiroUtil.getUserId());
        if (roleDAO.update(rolePO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        } else if (!roleDB.getKey().equals(rolePO.getKey())) {
            ShiroUtil.clearRoleAuthorization(roleDB.getKey());
        }

        Set<Integer> menuSet = roleDB.getMenus()
                .stream()
                .map(MenuPO::getId)
                .collect(Collectors.toSet());
        if (roleDB.getMenus().size() != roleDTO.getMenus().size() ||
                roleDTO.getMenus().stream()
                        .noneMatch(menuSet::contains)) {
            if (roleMenuDAO.remove(rolePO.getId()) < 1) {
                return Result.fail(ResultEnum.LATER_RETRY);
            } else if (roleMenuDAO.saveList(roleDTO.getMenus()
                    .stream()
                    .map(menuId -> new RoleMenuPO()
                            .setMenuId(menuId)
                            .setRoleId(rolePO.getId()))
                    .collect(Collectors.toList())) != roleDTO.getMenus().size()) {
                throw new GlobalException(ResultEnum.LATER_RETRY);
            }
            ShiroUtil.clearRoleAuthorization(roleDB.getKey());
        }
        return Result.success();

    }

    @Override
    public RoleVO getVO(RoleQuery query) {
        return roleDAO.getVO(query);
    }

    @Override
    public List<RoleSelectVO> listSelectVO(RoleQuery query) {
        return roleDAO.listSelectVO(query);
    }


    @Override
    public List<RoleTableVO> listTableVO(RoleQuery query, Page page) {
        startPage(page);
        return roleDAO.listTableVO(query);
    }

    @Override
    public Set<String> keySet(Integer userId) {
        return roleDAO.keySet(userId);
    }
}
