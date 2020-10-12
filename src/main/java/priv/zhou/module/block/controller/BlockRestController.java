package priv.zhou.module.block.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.block.domain.dto.BlockDTO;
import priv.zhou.module.block.service.IBlockService;

/**
 * 阻塞 控制层
 *
 * @author zhou
 * @since 2020.09.18
 */
@RestController
@RequestMapping("/block/rest")
public class BlockRestController {

    private final IBlockService blockService;

    public BlockRestController(IBlockService blockService) {
        this.blockService = blockService;
    }

    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return blockService.remove(new BlockDTO().setId(id));
    }

    @RequestMapping("/list")
    public OutVO<ListVO<BlockDTO>> list(BlockDTO blockDTO, Page page) {
        return blockService.list(blockDTO, page);
    }

}
