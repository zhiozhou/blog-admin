package priv.zhou.module.block.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
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
public class BlockServiceImpl implements IBlockService {

    private final BlockDAO blockDAO;

    public BlockServiceImpl(BlockDAO blockDAO) {
        this.blockDAO = blockDAO;
    }

    @Override
    public OutVO<NULL> save(BlockDTO blockDTO) {
        if (StringUtils.isBlank(blockDTO.getIp()) || null == blockDTO.getType()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return blockDAO.save(blockDTO.toPO()
                .setCreator(new UserPO().setId(ShiroUtil.getUserId()))) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }

    @Override
    public OutVO<NULL> remove(BlockDTO blockDTO) {
        BlockPO blockPO = blockDAO.get(blockDTO);
        if (null == blockPO) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        } else if (blockDAO.update(blockPO.setGmtFreed(new Date())) < 1) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        }
        return OutVO.success();
    }

    @Override
    public OutVO<BlockDTO> get(BlockDTO blockDTO) {

        BlockPO blockPO = blockDAO.get(blockDTO);
        return OutVO.success(new BlockDTO(blockPO));
    }

    @Override
    public OutVO<ListVO<BlockDTO>> list(BlockDTO blockDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<BlockPO> poList = blockDAO.list(blockDTO);
        PageInfo<BlockPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, BlockDTO::new), pageInfo.getTotal());
    }
}
