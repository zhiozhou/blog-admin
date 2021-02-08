package priv.zhou.module.system.role.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.constant.NULL;
import priv.zhou.module.system.role.domain.dto.RoleDTO;

import java.util.List;
import java.util.Set;

public interface IRoleService {


    Result<NULL> save(RoleDTO roleDTO);

    Result<NULL> remove(RoleDTO roleDTO);

    Result<NULL> update(RoleDTO roleDTO);

    Result<RoleDTO> get(RoleDTO roleDTO);

    default Result<List<RoleDTO>> list(RoleDTO roleDTO) {
        return list(roleDTO, null);
    }

    Result<List<RoleDTO>> list(RoleDTO roleDTO, Page page);

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(Integer userId);
}
