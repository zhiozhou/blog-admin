package priv.zhou.module.blog.controller;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseViewController;
import priv.zhou.module.blog.domain.query.BlogQuery;
import priv.zhou.module.blog.domain.vo.BlogVO;
import priv.zhou.module.blog.service.IBlogService;

import static priv.zhou.module.blog.service.IBlogService.STATE_DICT_KEY;

/**
 * 博客 视图控制层
 *
 * @author zhou
 * @since 2020.09.14
 */
@Component
@RequestMapping("/blog")
public class BlogViewController extends BaseViewController {

    private final IBlogService blogService;

    public BlogViewController(IBlogService blogService) {
        super("博客", "blog");
        this.blogService = blogService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new BlogVO().setTags(Lists.newArrayList()));

        return "blog/au";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        super.update(model, blogService.getVO(new BlogQuery().setId(id)));

        return "blog/au";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        super.detail(model, blogService.getVO(new BlogQuery().setId(id)));

        return "blog/detail";
    }

    @GetMapping
    public String view(Model model) {
        super.list(model);
        model.addAttribute("stateMap", dictService.mapDataVO(STATE_DICT_KEY, DICT_NORM_TYPE));
        return "blog/index";
    }
}
