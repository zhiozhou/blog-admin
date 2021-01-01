package priv.zhou.module.block.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.NULL;
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

    OutVO<NULL> remove(BlockDTO blockDTO);

    OutVO<BlockDTO> get(BlockDTO blockDTO);

    default OutVO<TableVO<BlockDTO>> list(BlockDTO blockDTO) {
        return list(blockDTO, null);
    }

    OutVO<TableVO<BlockDTO>> list(BlockDTO blockDTO, Page page);


}
