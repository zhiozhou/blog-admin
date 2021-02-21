package priv.zhou.module.system.menu.service;

import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.Tree;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;
import priv.zhou.module.system.menu.domain.query.MenuQuery;
import priv.zhou.module.system.menu.domain.vo.MenuTableVO;
import priv.zhou.module.system.menu.domain.vo.MenuVO;

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

    MenuVO listVO(MenuQuery query);

    List<MenuTableVO> listTableVO(MenuQuery query);

    default List<MenuDTO> tree(MenuDTO menuDTO) {
        Result<List<MenuDTO>> listRes = (menuDTO);
        return listRes.isFail() ? null : Tree.term(listRes.getData(), ROOT_ID);
    }

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(MenuQuery menuQuery);


}
