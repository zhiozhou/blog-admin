package priv.zhou.module.blog.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.service.IBlogService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

import static priv.zhou.common.param.CONSTANT.SYSTEM_USER_STATE;
import static priv.zhou.module.blog.service.IBlogService.STATE_KEY;

/**
 * 博客 视图控制层
 *
 * @author zhou
 * @since 2020.09.14
 */
@Component
@RequestMapping("/blog")
public class BlogController extends BaseController {

    private final IBlogService blogService;

    public BlogController(IBlogService blogService) {
        super(new Module("博客","blog"));
        this.blogService = blogService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new BlogDTO());

        return "blog/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());

        return "blog/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.detail(model, dtoVO.getData());

        return "blog/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);
        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(STATE_KEY)).getData());
        return "blog/list";
    }
}
