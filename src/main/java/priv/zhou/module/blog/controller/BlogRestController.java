package priv.zhou.module.blog.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.dto.TagDTO;
import priv.zhou.module.blog.service.IBlogService;

import javax.validation.Valid;
import java.util.List;

/**
 * 博客 控制层
 *
 * @author zhou
 * @since 2020.09.14
 */
@RestController
@RequestMapping("/blog/rest")
public class BlogRestController {

    private final IBlogService blogService;

    public BlogRestController(IBlogService blogService) {
        this.blogService = blogService;
    }


    @RequestMapping("/save")
    public Result<NULL> save(@Valid BlogDTO blogDTO) {
        return blogService.save(blogDTO);
    }

    @RequestMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return blogService.remove(new BlogDTO().setId(id));
    }

    @RequestMapping("/update")
    public Result<NULL> update(@Valid BlogDTO blogDTO) {
        return blogService.update(blogDTO);
    }


    @RequestMapping("/list")
    public Result<TableVO<BlogDTO>> list(BlogDTO blogDTO, Page page) {
        return Result.table(blogService.list(blogDTO, page));
    }


    @RequestMapping("/tag/list")
    public Result<List<TagDTO>> list(TagDTO tagDTO) {
        return blogService.tagList(tagDTO);
    }

}
