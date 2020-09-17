package priv.zhou.module.system.menu.service;

import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.system.menu.domain.dto.MenuDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public interface IMenuService {


    Integer ADMIN_FLAG = 1;

    Integer SERVICE_FLAG = 2;

    OutVO<NULL> save(MenuDTO menuDTO);

    OutVO<NULL> remove(MenuDTO menuDTO);

    OutVO<NULL> update(MenuDTO menuDTO);

    OutVO<MenuDTO> get(MenuDTO menuDTO);

    OutVO<List<MenuDTO>> list(MenuDTO menuDTO);

    /**
     * 获取用户的权限字符set
     */
    Set<String> keySet(MenuDTO menuDTO);

    /**
     * 结构整理为 dto.childList 格式
     */
    static List<MenuDTO> toTree(List<MenuDTO> dtoList) {
        Integer rootId = 0;
        TreeMap<Integer, List<MenuDTO>> groupMap = dtoList.stream().collect(Collectors.groupingBy(MenuDTO::getParentId, TreeMap::new, Collectors.toList()));
        List<MenuDTO> outList = groupMap.get(rootId);
        for (Map.Entry<Integer, List<MenuDTO>> entry : groupMap.entrySet()) {
            if (!rootId.equals(entry.getKey())) {
                MenuDTO parent = getMenu(dtoList, entry.getKey());
                parent.setChildList(entry.getValue());
            }
        }
        return outList;
    }

    static MenuDTO getMenu(List<MenuDTO> poList, Integer id) {
        return poList.stream().filter(po -> id.equals(po.getId())).findFirst().orElse(null);
    }
}
