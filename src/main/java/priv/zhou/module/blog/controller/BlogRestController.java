package priv.zhou.module.blog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.query.BlogQuery;
import priv.zhou.module.blog.domain.query.TagQuery;
import priv.zhou.module.blog.domain.vo.BlogTableVO;
import priv.zhou.module.blog.domain.vo.TagVO;
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


    @PostMapping("/save")
    public Result<NULL> save(@Valid BlogDTO blogDTO) {
        return blogService.save(blogDTO);
    }

    @PostMapping("/remove/{id}")
    public Result<NULL> remove(@RequestParam(value = "ids") Integer id) {
        return blogService.remove(id);
    }

    @PostMapping("/update")
    public Result<NULL> update(@Valid BlogDTO blogDTO) {
        return blogService.update(blogDTO);
    }


    @PostMapping("/list")
    public Result<TableVO<BlogTableVO>> list(BlogQuery query, Page page) {
        return Result.table(blogService.listTableVO(query, page));
    }


    @PostMapping("/tag/list")
    public Result<List<TagVO>> listTag(TagQuery query) {
        return Result.success(blogService.listTag(query));
    }

}
