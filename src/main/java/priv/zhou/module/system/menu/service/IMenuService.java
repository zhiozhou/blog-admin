package priv.zhou.module.system.menu.service;

import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.Tree;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.domain.vo.MenuSelectVO;
import priv.zhou.module.system.menu.domain.vo.MenuTableVO;
import priv.zhou.module.system.menu.domain.vo.MenuVO;
import priv.zhou.module.system.user.domain.bo.UserPrincipal;

import java.util.List;
import java.util.Set;

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
        return Tree.term(listVO(query), ROOT_ID);
    }

    default List<MenuSelectVO> treeSelectVO(MenuQuery query) {
        return Tree.term(listSelectVO(query), ROOT_ID);
    }

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(MenuQuery menuQuery);


}
