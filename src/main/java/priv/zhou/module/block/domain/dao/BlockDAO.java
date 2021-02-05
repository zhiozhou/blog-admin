package priv.zhou.module.block.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO1;
import priv.zhou.module.block.domain.dto.BlockDTO;
import priv.zhou.module.block.domain.po.BlockPO;


/**
 * 阻塞 数据访问模型
 *
 * @author zhou
 * @since 2020.09.18
 */
@Mapper
@Component
public interface BlockDAO extends BaseDAO1<BlockDTO, BlockPO> {
}
