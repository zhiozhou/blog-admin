package priv.zhou.module.system.role.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.common.domain.dao.BaseDAO1;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.role.domain.po.RoleMenuPO;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.domain.query.RoleQuery;
import priv.zhou.module.system.role.domain.vo.RoleTableVO;
import priv.zhou.module.system.role.domain.vo.RoleVO;
import priv.zhou.module.system.user.domain.po.UserRolePO;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface RoleDAO extends BaseDAO<RolePO, RoleQuery> {

    int removeList(Integer[] ids);

    RoleVO getVO(RoleQuery query);

    List<RoleTableVO> listTableVO(RoleQuery query);

    int relateMenu(List<RoleMenuPO> userRoles);

    int unRelateMenu(Integer roleId);

    int countUser(RoleQuery query);

    Set<Integer> idSet(Integer userId);

    Set<String> keySet(Integer userId);

}

