package priv.zhou.module.blog.blogType.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.service.IBlogTypeService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

/**
 * 博客类型 视图控制层
 *
 * @author zhou
 * @since 2020.05.21
 */
@Component
@RequestMapping("/blog/type")
public class BlogTypeController extends BaseController {

    private final IBlogTypeService blogTypeService;

    private final String STATE_KEY = "blog_type_state";

    private final Module module = new Module("", "blog:type");

    public BlogTypeController(IBlogTypeService blogTypeService) {
        this.blogTypeService = blogTypeService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new BlogTypeDTO());
        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(STATE_KEY)).getData());
        return "blog/blogType/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<BlogTypeDTO> dtoVO = blogTypeService.get(new BlogTypeDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());
        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(STATE_KEY)).getData());
        return "blog/blogType/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<BlogTypeDTO> dtoVO = blogTypeService.get(new BlogTypeDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        BlogTypeDTO blogTypeDTO = dtoVO.getData();
        blogTypeDTO.setStateStr(dictService.getData(new DictDTO().setKey(STATE_KEY).setCode(blogTypeDTO.getState())).getData().getLabel());
        super.detail(model, dtoVO.getData());

        return "blog/blogType/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model, module);

        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(STATE_KEY)).getData());
        return "blog/blogType/list";
    }
}
