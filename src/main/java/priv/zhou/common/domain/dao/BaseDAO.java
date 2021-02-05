package priv.zhou.common.domain.dao;

import java.util.List;

public interface BaseDAO<PO, Query> {

    int save(PO po);

    int remove(Query query);

    int update(PO po);

    PO get(Query query);

    List<PO> list(Query query);

    int count(Query query);
}
