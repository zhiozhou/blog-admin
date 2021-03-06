package priv.zhou.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.framework.exception.RestException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;


@Getter
@Setter
@Accessors(chain = true)
public class Tree {

    /**
     * id
     */
    protected Integer id;

    /**
     * 父级id
     */
    protected Integer parentId;

    /**
     * 名称
     */
    protected String name;

    /**
     * 子级
     */
    protected List<Tree> children;


    /**
     * 树对象组整理为树形结构
     */
    @SuppressWarnings("unchecked")
    public static <M extends Tree> List<M> term(List<M> treeList, Integer rootId) {
        if(null == treeList || treeList.isEmpty()){
            return treeList;
        }
        TreeMap<Integer, List<Tree>> groupMap = treeList.stream().collect(Collectors.groupingBy(Tree::getParentId, TreeMap::new, Collectors.toList()));
        for (Map.Entry<Integer, List<Tree>> entry : groupMap.entrySet()) {
            if (!rootId.equals(entry.getKey())) {
                treeList.stream()
                        .filter(po -> entry.getKey().equals(po.getId()))
                        .findFirst()
                        .orElseThrow(() -> new RestException(ResultEnum.FAIL_DATA, "父级菜单不存在: id=" + entry.getKey()))
                        .setChildren(entry.getValue());
            }
        }
        return (List<M>) groupMap.get(rootId);
    }
}
