package priv.zhou.module.system.user.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.user.domain.bo.UserBO;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;
import priv.zhou.module.system.user.domain.po.UserPO;
import priv.zhou.module.system.user.domain.po.UserRolePO;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.query.UserRoleQuery;
import priv.zhou.module.system.user.domain.vo.UserTableVO;
import priv.zhou.module.system.user.domain.vo.UserVO;

import java.util.List;

@Mapper
@Repository
public interface UserRoleDAO extends BaseDAO<UserRolePO, UserRoleQuery> {

    int saveList(List<UserRolePO> userRoles);

    int remove(Integer userId);

}
