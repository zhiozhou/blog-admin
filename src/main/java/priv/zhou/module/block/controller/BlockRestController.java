package priv.zhou.module.block.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.interfaces.NULL;
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

    @PostMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return blockService.remove(new BlockDTO().setId(id));
    }

    @PostMapping("/list")
    public Result<TableVO<BlockDTO>> list(BlockDTO blockDTO, Page page) {
        return Result.table(blockService.list(blockDTO, page));
    }

}
