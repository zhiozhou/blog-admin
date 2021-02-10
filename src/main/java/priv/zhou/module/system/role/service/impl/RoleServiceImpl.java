package priv.zhou.module.system.role.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.PinyinUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.service.IRoleService;
import priv.zhou.module.system.user.domain.dao.UserDAO;
import priv.zhou.module.system.user.domain.query.UserQuery;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseService implements IRoleService {

    private final UserDAO userDAO;

    private final RoleDAO roleDAO;


    @Override
    public Result<NULL> save(RoleDTO roleDTO) {

        // 1.转换类型
        RolePO rolePO = roleDTO.toPO();

        // 2.补充参数
        rolePO.setCreateId(ShiroUtil.getUserId());
        if (StringUtils.isBlank(rolePO.getKey())) {
            rolePO.setKey(PinyinUtil.toPinyin(rolePO.getName()));
        }

        // 3.验证参数
        if (roleDAO.count(new RoleDTO().setName(roleDTO.getName())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleDTO().setKey(rolePO.getKey())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        // 4.保存角色
        if (roleDAO.save(rolePO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        }

        // 5.保存菜单
        if (null != rolePO.getMenuList()) {
            roleDAO.saveMenu(rolePO);
        }
        return Result.success();
    }

    @Override
    public Result<NULL> remove(RoleDTO roleDTO) {
        if (null == roleDTO.getId()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        } else if (roleDAO.countUser(roleDTO) > 0) {
            return Result.fail(ResultEnum.EXIST_RELATION, "角色下尚有用户，不可删除");
        } else if (roleDAO.remove(roleDTO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        }
        roleDAO.clearMenu(roleDTO);
        return Result.success();
    }

    @Override
    public Result<NULL> update(RoleDTO roleDTO) {

        // 1.转换类型
        RolePO rolePO = roleDTO.toPO();

        // 2.验证参数
        if (roleDAO.count(new RoleDTO().setName(rolePO.getName()).setExclId(rolePO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleDTO().setKey(rolePO.getKey()).setExclId(roleDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        // 3.补充参数
        rolePO.setModifiedId(ShiroUtil.getUserId());

        if (roleDAO.update(rolePO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
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
