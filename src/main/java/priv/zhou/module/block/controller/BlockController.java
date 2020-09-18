package priv.zhou.module.block.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.block.domain.dto.BlockDTO;
import priv.zhou.module.block.service.IBlockService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

import static priv.zhou.common.param.CONSTANT.SYSTEM_MENU_TYPE;
import static priv.zhou.module.block.service.IBlockService.TYPE_KEY;

/**
 * 阻塞 视图控制层
 *
 * @author zhou
 * @since 2020.09.18
 */

@Component
@RequestMapping("/block")
public class BlockController extends BaseController {
    
    public BlockController() {
        super(new Module("阻塞","block"));
    }

    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);
        model.addAttribute("typeMap", dictService.dataMap(new DictDTO().setKey(TYPE_KEY)).getData());
        return "block/list";
    }
}
