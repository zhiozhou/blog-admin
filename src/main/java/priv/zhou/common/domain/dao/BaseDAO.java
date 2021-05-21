package priv.zhou.common.domain.dao;

import java.util.List;

/**
 * 基础数据访问模型
 *
 * @author zhou
 * @since 0.1.0
 */
public interface BaseDAO<PO, Query> {

    int save(PO po);

    int update(PO po);

    PO get(Query query);

    List<PO> list(Query query);

    int count(Query query);
}
