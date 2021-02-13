package priv.zhou.module.system.role.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO1;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RolePO;

import java.util.Set;

@Mapper
@Component
public interface RoleDAO extends BaseDAO1<RoleDTO, RolePO> {

    int clearMenu(RoleDTO roleDTO);

    int saveMenu(RolePO rolePO);

    int countUser(RoleDTO roleDTO);

    Set<Integer> idSet(Integer userId);

    Set<String> keySet(Integer userId);

}
