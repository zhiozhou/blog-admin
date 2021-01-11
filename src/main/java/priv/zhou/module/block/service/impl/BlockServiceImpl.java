package priv.zhou.module.block.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.block.domain.dao.BlockDAO;
import priv.zhou.module.block.domain.dto.BlockDTO;
import priv.zhou.module.block.domain.po.BlockPO;
import priv.zhou.module.block.service.IBlockService;
import priv.zhou.module.system.user.domain.po.UserPO;

import java.util.Date;
import java.util.List;


/**
 * 阻塞 服务层实现
 *
 * @author zhou
 * @since 2020.09.18
 */
@Service
public class BlockServiceImpl extends BaseService implements IBlockService {

    private final BlockDAO blockDAO;

    public BlockServiceImpl(BlockDAO blockDAO) {
        this.blockDAO = blockDAO;
    }

    @Override
    public Result<NULL> save(BlockDTO blockDTO) {
        if (StringUtils.isBlank(blockDTO.getIp()) || null == blockDTO.getType()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }

        return blockDAO.save(blockDTO.toPO()
                .setCreator(new UserPO().setId(ShiroUtil.getUserId()))) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.FAIL_OPERATION);

    }

    @Override
    public Result<NULL> remove(BlockDTO blockDTO) {
        BlockPO blockPO = blockDAO.get(blockDTO);
        if (null == blockPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        } else if (blockDAO.update(blockPO.setGmtFreed(new Date())) < 1) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        }
        return Result.success();
    }

    @Override
    public Result<BlockDTO> get(BlockDTO blockDTO) {

        BlockPO blockPO = blockDAO.get(blockDTO);
        return Result.success(new BlockDTO(blockPO));
    }

    @Override
    public Result<List<BlockDTO>> list(BlockDTO blockDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(blockDAO.list(blockDTO), BlockDTO::new));
    }
}
