package priv.zhou.module.system.menu.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.menu.domain.po.MenuPO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface MenuDAO extends BaseDAO<MenuPO, MenuQuery> {

    Set<String> keySet(MenuQuery query);

    List<MenuPO> listTableVO(MenuQuery query);
}
