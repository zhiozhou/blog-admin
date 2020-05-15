package priv.zhou.module.system.role.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RolePO;

import java.util.Set;

@Mapper
@Component
public interface RoleDAO extends BaseDAO<RoleDTO, RolePO> {

    Integer clearMenu(RoleDTO roleDTO);

    Integer saveMenu(RolePO rolePO);

    Integer countUser(RoleDTO roleDTO);

    Set<String> keySet(Integer userId);
}
