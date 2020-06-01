package priv.zhou.common.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dto.DTO;

import java.util.List;


/**
 * 基础 数据访问模型
 * 限定义基础领域入参与返回
 * @author zhou
 * @since 2020.04.30
 */
@Mapper
@Component
public interface BaseDAO<DTO extends priv.zhou.common.domain.dto.DTO<PO>,PO> {

    Integer save(PO po);

    void remove(DTO dto);

    Integer update(PO po);

    PO get(DTO dto);

    List<PO> list(DTO dto);

    Integer count(DTO dto);
}
