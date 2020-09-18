package priv.zhou.module.block.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.block.domain.dto.BlockDTO;

/**
 * 阻塞 服务层定义
 *
 * @author zhou
 * @since 2020.09.18
 */
public interface IBlockService {

    String TYPE_KEY = "block_type";

    OutVO<NULL> save(BlockDTO blockDTO);

    OutVO<BlockDTO> get(BlockDTO blockDTO);

    OutVO<ListVO<BlockDTO>> list(BlockDTO blockDTO, Page page);
}
