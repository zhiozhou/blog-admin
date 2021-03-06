package priv.zhou.module.system.user.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.user.domain.bo.UserBO;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;
import priv.zhou.module.system.user.domain.po.UserPO;
import priv.zhou.module.system.user.domain.po.UserRolePO;
import priv.zhou.module.system.user.domain.query.UserQuery;
import priv.zhou.module.system.user.domain.vo.UserTableVO;
import priv.zhou.module.system.user.domain.vo.UserVO;

import java.util.List;

@Mapper
@Repository
public interface UserDAO extends BaseDAO<UserPO, UserQuery> {

    int remove(Integer ids);

    List<UserTableVO> listTableVO(UserQuery userQuery);

    UserBO getBO(UserQuery userQuery);

    UserVO getVO(UserQuery userQuery);

    UserPrincipal getPrincipal(UserQuery userQuery);

}
