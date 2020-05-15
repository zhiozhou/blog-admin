package priv.zhou.common.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 案例 数据访问模型
 *
 * @author zhou
 * @since 2020.04.30
 */
@Mapper
@Component
public interface BaseDAO<DTO,PO> {

    Integer save(PO po);

    void remove(DTO dto);

    Integer update(PO po);

    PO get(DTO dto);

    List<PO> list(DTO dto);

    Integer count(DTO dto);
}
