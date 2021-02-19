package priv.zhou.module.system.role.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.PinyinUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.menu.domain.dao.MenuDAO;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RoleMenuPO;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.dao.UserDAO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseService implements IRoleService {

    private final MenuDAO menuDAO;

    private final RoleDAO roleDAO;


    @Override
    @Transactional
    public Result<NULL> save(RoleDTO roleDTO) {

        if (roleDAO.count(new RoleQuery().setName(roleDTO.getName())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleQuery().setKey(roleDTO.getKey())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        } else if (StringUtils.isBlank(roleDTO.getKey())) {
            roleDTO.setKey(PinyinUtil.toPinyin(roleDTO.getName()));
        }

        RolePO rolePO = new RolePO()
                .setKey(roleDTO.getKey())
                .setName(roleDTO.getName())
                .setRemark(roleDTO.getRemark())
                .setState(0)
                .setCreateId(ShiroUtil.getUserId());
        if (roleDAO.save(rolePO) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (null != roleDTO.getMenus() && roleDAO.relateMenu(roleDTO.getMenus()
                .stream()
                .map(menu -> new RoleMenuPO()
                        .setRoleId(rolePO.getId())
                        .setMenuId(menu.getId()))
                .collect(Collectors.toList())) != roleDTO.getMenus().size()) {
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> remove(Integer[] ids) {
        if (roleDAO.countUser(new RoleQuery().setIds(ids)) > 0) {
            return Result.fail(ResultEnum.EXIST_RELATION, "角色下尚有用户，不可删除");
        } else if (roleDAO.removeList(ids) != ids.length) {
            throw new GlobalException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    public Result<NULL> update(RoleDTO roleDTO) {

        if (roleDAO.count(new RoleQuery().setName(roleDTO.getName()).setRidId(roleDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleQuery().setKey(roleDTO.getKey()).setRidId(roleDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }


        RolePO rolePO = new RolePO()
                .setKey(roleDTO.getKey())
                .setName(roleDTO.getName())
                .setRemark(roleDTO.getRemark())
                .setModifiedId(ShiroUtil.getUserId());
        if (roleDAO.update(rolePO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        }else if (){

        }

        // 4.清除菜单
        roleDAO.clearMenu(roleDTO);

        // 5.保存菜单
        if (null != rolePO.getMenuList()) {
            roleDAO.saveMenu(rolePO);
        }

        // 6.清除授权
        ShiroUtil.clearRoleAuthorization(rolePO.getName());
        return Result.success();

    }

    @Override
    public Result<RoleDTO> get(RoleDTO roleDTO) {

        RolePO rolePO = roleDAO.get(roleDTO);
        if (null == rolePO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }
        return Result.success(new RoleDTO(rolePO));
    }


    @Override
    public Result<List<RoleDTO>> list(RoleDTO roleDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(roleDAO.list(roleDTO), RoleDTO::new));
    }

    @Override
    public Set<String> keySet(Integer userId) {
        return roleDAO.keySet(userId);
    }
}
