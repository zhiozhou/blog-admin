package priv.zhou.module.blog.blogType.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.service.IBlogTypeService;

/**
 *  视图控制层
 *
 * @author zhou
 * @since 2020.05.21
 */
@Component
@RequestMapping("/blog/type")
public class BlogTypeController extends BaseController {

    private IBlogTypeService blogTypeService;

    private Module module = new Module("","blog:blogType");

    public BlogTypeController(IBlogTypeService blogTypeService) {
        this.blogTypeService = blogTypeService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        fillAdd(model, new BlogTypeDTO());

        return "blog/blogType/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<BlogTypeDTO> dtoVO = blogTypeService.get(new BlogTypeDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillUpdate(model, dtoVO.getData());

        return "blog/blogType/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<BlogTypeDTO> dtoVO = blogTypeService.get(new BlogTypeDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillDetail(model, dtoVO.getData());

        return "blog/blogType/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);

        return "blog/blogType/list";
    }
}
