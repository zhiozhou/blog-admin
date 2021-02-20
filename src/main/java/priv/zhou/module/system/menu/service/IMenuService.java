package priv.zhou.module.system.menu.service;

import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.Tree;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;

import java.util.List;
import java.util.Set;

public interface IMenuService {

    Integer ROOT_ID = 0;

    Integer ADMIN_FLAG = 1;

    Integer SERVICE_FLAG = 2;

    Result<NULL> save(MenuDTO menuDTO);

    Result<NULL> remove(MenuDTO menuDTO);

    Result<NULL> update(MenuDTO menuDTO);

    Result<MenuDTO> get(MenuDTO menuDTO);

    Result<List<MenuDTO>> list(MenuDTO menuDTO);

    default List<MenuDTO> tree(MenuDTO menuDTO) {
        Result<List<MenuDTO>> listRes = list(menuDTO);
        return listRes.isFail() ? null : Tree.term(listRes.getData(), ROOT_ID);
    }

    /**
     * 结构整理为 dto.childList 属性结构
     */

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(MenuDTO menuDTO);


}
