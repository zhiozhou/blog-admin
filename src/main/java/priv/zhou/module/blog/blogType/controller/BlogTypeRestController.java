package priv.zhou.module.blog.blogType.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.service.IBlogTypeService;

import javax.validation.Valid;

/**
 *  控制层
 *
 * @author zhou
 * @since 2020.05.21
 */
@RestController
@RequestMapping("/blog/type/rest")
public class BlogTypeRestController {

    private IBlogTypeService blogTypeService;

    public BlogTypeRestController(IBlogTypeService blogTypeService) {
        this.blogTypeService = blogTypeService;
    }

    @RequestMapping("/save")
    public OutVO<NULL> save(@Valid BlogTypeDTO blogTypeDTO) {
        return blogTypeService.save(blogTypeDTO);
    }

    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return blogTypeService.remove(new BlogTypeDTO().setId(id));
    }

    @RequestMapping("/update")
    public OutVO<NULL> update(@Valid BlogTypeDTO blogTypeDTO) {
        return blogTypeService.update(blogTypeDTO);
    }

    @RequestMapping("/list")
    public OutVO<ListVO<BlogTypeDTO>> list(BlogTypeDTO blogTypeDTO, Page page) {
        return blogTypeService.list(blogTypeDTO, page);
    }

}
