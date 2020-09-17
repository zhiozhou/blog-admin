package priv.zhou.module.system.user.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.user.domain.dto.UserDTO;
import priv.zhou.module.system.user.domain.po.UserPO;

@Mapper
@Component
public interface UserDAO extends BaseDAO<UserDTO,UserPO> {

    int saveRole(UserPO userPO);

    int removeRole(UserPO userPO);
}
