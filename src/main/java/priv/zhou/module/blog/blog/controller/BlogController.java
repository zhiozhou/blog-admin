package priv.zhou.module.blog.blog.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.blog.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.blog.service.IBlogService;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.service.IBlogTypeService;

/**
 * 视图控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@Component
@RequestMapping("/blog/blog")
public class BlogController extends BaseController {

    private final IBlogService blogService;

    private final IBlogTypeService blogTypeService;


    private final Module module = new Module("", "blog:blog");

    public BlogController(IBlogService blogService, IBlogTypeService blogTypeService) {
        this.blogService = blogService;
        this.blogTypeService = blogTypeService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        fillAdd(model, new BlogDTO());

        model.addAttribute("typeList", blogTypeService.list(new BlogTypeDTO(), new Page(0)).getData().getList());
        return "blog/blog/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillUpdate(model, dtoVO.getData());
        model.addAttribute("typeList", blogTypeService.list(new BlogTypeDTO(), new Page(0)).getData().getList());
        return "blog/blog/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillDetail(model, dtoVO.getData());
        return "blog/blog/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);
        model.addAttribute("typeList", blogTypeService.list(new BlogTypeDTO(), new Page(0)).getData().getList());
        return "blog/blog/list";
    }
}
