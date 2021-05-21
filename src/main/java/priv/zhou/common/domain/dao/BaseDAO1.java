package priv.zhou.common.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dto.Order;

import java.util.List;


/**
 * 废弃 等待完成删除
 * @deprecated
 */
@Mapper
@Component
public interface BaseDAO1<DTO extends priv.zhou.common.domain.dto.DTO<PO>, PO> {

    int save(PO po);

    int remove(DTO dto);

    int update(PO po);

    PO get(DTO dto);

    List<PO> list(DTO dto);

    List<PO> list(@Param("dto") DTO dto, @Param("order") Order order);

    int count(DTO dto);
}
