package priv.zhou.module.system.role.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import priv.zhou.module.system.role.domain.po.RoleMenuPO;

import java.util.List;

@Mapper
@Repository
public interface RoleMenuDAO {

    int saveList(List<RoleMenuPO> userRoles);

    int remove(Integer roleId);

    /**
     * 整理垃圾关联
     */
    void trim();

}

