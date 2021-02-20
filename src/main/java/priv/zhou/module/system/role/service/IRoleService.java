package priv.zhou.module.system.role.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.constant.NULL;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.domain.vo.RoleSelectVO;
import priv.zhou.module.system.role.domain.vo.RoleTableVO;
import priv.zhou.module.system.role.domain.vo.RoleVO;

import java.util.List;
import java.util.Set;

public interface IRoleService {


    Result<NULL> save(RoleDTO roleDTO);

    Result<NULL> remove(Integer[] ids);

    Result<NULL> update(RoleDTO roleDTO);

    RoleVO getVO(RoleQuery query);

    List<RoleSelectVO> listSelectVO(RoleQuery query);

    List<RoleTableVO> listTableVO(RoleQuery query, Page page);

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(Integer userId);
}
