package priv.zhou.module.system.role.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.PinyinUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.role.domain.dao.RoleDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.service.IRoleService;

import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
public class RoleServiceImpl implements IRoleService {

    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public OutVO<NULL> save(RoleDTO roleDTO) {

        // 1.转换类型
        RolePO rolePO = roleDTO.toPO();

        // 2.补充参数
        rolePO.setCreateId(ShiroUtil.getUserId());
        if (StringUtils.isBlank(rolePO.getKey())) {
            rolePO.setKey(PinyinUtil.toPinyin(rolePO.getName()));
        }

        // 3.验证参数
        if (roleDAO.count(new RoleDTO().setName(roleDTO.getName())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleDTO().setKey(rolePO.getKey())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 4.保存角色
        if (roleDAO.save(rolePO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        // 5.保存菜单
        if (null != rolePO.getMenuList()) {
            roleDAO.saveMenu(rolePO);
        }
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> remove(RoleDTO roleDTO) {
        if (null == roleDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if (roleDAO.countUser(roleDTO) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_RELATION, "角色下尚有用户，不可删除");
        }else if (roleDAO.remove(roleDTO)<1){
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }
        roleDAO.clearMenu(roleDTO);
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(RoleDTO roleDTO) {

        // 1.转换类型
        RolePO rolePO = roleDTO.toPO();

        // 2.验证参数
        if (roleDAO.count(new RoleDTO().setName(rolePO.getName()).setExclId(rolePO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (roleDAO.count(new RoleDTO().setKey(rolePO.getKey()).setExclId(roleDTO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 3.补充参数
        rolePO.setModifiedId(ShiroUtil.getUserId());

        if (roleDAO.update(rolePO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        // 4.清除菜单
        roleDAO.clearMenu(roleDTO);

        // 5.保存菜单
        if (null != rolePO.getMenuList()) {
            roleDAO.saveMenu(rolePO);
        }

        // 6.清除授权
        ShiroUtil.getUserRealm().clearAllCachedAuthorizationInfo();
        return OutVO.success();

    }

    @Override
    public OutVO<RoleDTO> get(RoleDTO roleDTO) {

        RolePO rolePO = roleDAO.get(roleDTO);
        if (null == rolePO) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        }
        return OutVO.success(new RoleDTO(rolePO));
    }


    @Override
    public OutVO<ListVO<RoleDTO>> list(RoleDTO roleDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<RolePO> poList = roleDAO.list(roleDTO);
        PageInfo<RolePO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, RoleDTO::new), pageInfo.getTotal());
    }

    @Override
    public Set<String> keySet(Integer userId) {
        return roleDAO.keySet(userId);
    }
}
