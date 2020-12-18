package priv.zhou.module.system.role.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;

import java.util.Set;

public interface IRoleService {


    OutVO<NULL> save(RoleDTO roleDTO);

    OutVO<NULL> remove(RoleDTO roleDTO);

    OutVO<NULL> update(RoleDTO roleDTO);

    OutVO<RoleDTO> get(RoleDTO roleDTO);

    OutVO<ListVO<RoleDTO>> list(RoleDTO roleDTO, Page page);

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(Integer userId);
}
