package priv.zhou.module.block.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseViewController;

import static priv.zhou.module.block.service.IBlockService.TYPE_KEY;

/**
 * 阻塞 视图控制层
 *
 * @author zhou
 * @since 2020.09.18
 */

@Component
@RequestMapping("/block")
public class BlockViewController extends BaseViewController {

    public BlockViewController() {
        super("阻塞", "block");
    }

    @GetMapping
    public String view(Model model) {
        super.list(model);
        model.addAttribute("typeMap", dictService.mapDataVO(TYPE_KEY, DICT_NORM_TYPE));
        return "block/index";
    }
}
