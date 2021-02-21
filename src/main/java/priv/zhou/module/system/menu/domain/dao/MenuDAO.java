package priv.zhou.module.system.menu.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.menu.domain.po.MenuPO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.domain.vo.MenuTableVO;
import priv.zhou.module.system.menu.domain.vo.MenuVO;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface MenuDAO extends BaseDAO<MenuPO, MenuQuery> {

    int removeTree(Integer id);

    MenuVO getVO(MenuQuery query);

    List<MenuTableVO> listTableVO(MenuQuery query);

    Set<String> keySet(MenuQuery query);

}
