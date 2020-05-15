package priv.zhou.module.system.menu.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.po.MenuPO;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface MenuDAO extends BaseDAO<MenuDTO, MenuPO> {

    Set<String> keySet(Integer userId);


    List<MenuPO> childList(MenuDTO menuDTO);


}
