package priv.zhou.module.system.menu.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        return listRes.isFail() ? null : toTree(listRes.getData());
    }

    /**
     * 结构整理为 dto.childList 属性结构
     */
    static List<MenuDTO> toTree(List<MenuDTO> dtoList) {
        TreeMap<Integer, List<MenuDTO>> groupMap = dtoList.stream().collect(Collectors.groupingBy(MenuDTO::getParentId, TreeMap::new, Collectors.toList()));
        for (Map.Entry<Integer, List<MenuDTO>> entry : groupMap.entrySet()) {
            if (!ROOT_ID.equals(entry.getKey())) {
                dtoList.stream()
                        .filter(po -> entry.getKey().equals(po.getId()))
                        .findFirst()
                        .orElseThrow(() -> new GlobalException(ResultEnum.FAIL_DATA, "父级菜单不存在: id=" + entry.getKey()))
                        .setChildList(entry.getValue());
            }
        }
        return groupMap.get(ROOT_ID);
    }

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(MenuDTO menuDTO);


}
