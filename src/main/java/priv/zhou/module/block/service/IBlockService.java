package priv.zhou.module.block.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.module.block.domain.dto.BlockDTO;

import java.util.List;

/**
 * 阻塞 服务层定义
 *
 * @author zhou
 * @since 2020.09.18
 */
public interface IBlockService {

    String TYPE_KEY = "block_type";

    Result<NULL> save(BlockDTO blockDTO);

    Result<NULL> remove(BlockDTO blockDTO);

    Result<BlockDTO> get(BlockDTO blockDTO);

    default Result<List<BlockDTO>> list(BlockDTO blockDTO) {
        return list(blockDTO, null);
    }

    Result<List<BlockDTO>> list(BlockDTO blockDTO, Page page);


}
