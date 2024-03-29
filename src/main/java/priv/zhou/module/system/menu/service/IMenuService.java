package priv.zhou.module.system.menu.service;

import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.Tree;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.domain.vo.MenuSelectVO;
import priv.zhou.module.system.menu.domain.vo.MenuVO;

import java.util.List;

public interface IMenuService {

    Integer ROOT_ID = 0;

    Integer ADMIN_FLAG = 1;

    Integer SERVICE_FLAG = 2;

    Result<NULL> save(MenuDTO menuDTO);

    Result<NULL> remove(Integer id);

    Result<NULL> update(MenuDTO menuDTO);

    MenuVO getVO(MenuQuery query);

    List<MenuVO> listVO(MenuQuery query);

    List<MenuSelectVO> listSelectVO(MenuQuery query);

    default List<MenuVO> treeVO(MenuQuery query) {
        return Tree.trim(listVO(query), ROOT_ID);
    }

    default List<MenuSelectVO> treeSelectVO(MenuQuery query) {
        return Tree.trim(listSelectVO(query), ROOT_ID);
    }

}
