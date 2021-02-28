package priv.zhou.module.blog.controller;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Result;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.service.IBlogService;

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
        super("博客", "blog");
        this.blogService = blogService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new BlogDTO().setTags(Lists.newArrayList()));

        return "blog/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        Result<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());

        return "blog/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        Result<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.detail(model, dtoVO.getData());

        return "blog/detail";
    }

    @RequestMapping
    public String view(Model model) {
        super.list(model);
        model.addAttribute("stateMap", dictService.mapDataVO(STATE_KEY,DICT_NORM_TYPE));
        return "index";
    }
}
