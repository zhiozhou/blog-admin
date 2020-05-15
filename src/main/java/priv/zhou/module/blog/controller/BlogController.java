package priv.zhou.module.blog.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.service.IBlogService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

/**
 *  视图控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@Component
@RequestMapping("/blog")
public class BlogController extends BaseController {

    private IBlogService blogService;

    private final String TYPE_KEY = "blog_type";

    private Module module = new Module("","blog");

    public BlogController(IBlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        fillAdd(model, new BlogDTO());

        model.addAttribute("typeList", dictService.dataList(new DictDTO().setKey(TYPE_KEY)).getData());
        return "blog/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillUpdate(model, dtoVO.getData());
        model.addAttribute("typeList", dictService.dataList(new DictDTO().setKey(TYPE_KEY)).getData());
        return "blog/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<BlogDTO> dtoVO = blogService.get(new BlogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        BlogDTO blogDTO = dtoVO.getData();
        blogDTO.setTypeStr(dictService.getData(new DictDTO().setKey(TYPE_KEY).setCode(blogDTO.getType())).getData().getLabel());
        fillDetail(model, dtoVO.getData());
        return "blog/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);
        model.addAttribute("typeMap", dictService.dataMap(new DictDTO().setKey(TYPE_KEY)).getData());
        return "blog/list";
    }
}
