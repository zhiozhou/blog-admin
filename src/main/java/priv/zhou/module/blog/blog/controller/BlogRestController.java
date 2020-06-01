package priv.zhou.module.blog.blog.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.blog.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.blog.service.IBlogService;

import javax.validation.Valid;

/**
 * 博客 控制层
 *
 * @author zhou
 * @since 2020.05.15
 */
@RestController
@RequestMapping("/blog/blog/rest")
public class BlogRestController {

    private final IBlogService blogService;

    public BlogRestController(IBlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping("/save")
    public OutVO<NULL> save(@Valid BlogDTO blogDTO) {
        return blogService.save(blogDTO);
    }

    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return blogService.remove(new BlogDTO().setId(id));
    }

    @RequestMapping("/update")
    public OutVO<NULL> update(@Valid BlogDTO blogDTO) {
        return blogService.update(blogDTO);
    }

    @RequestMapping("/list")
    public OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        return blogService.list(blogDTO, page);
    }


}
