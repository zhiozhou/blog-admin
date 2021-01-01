package priv.zhou.module.blog.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.dto.TagDTO;

import java.util.List;

/**
 * 博客 服务层定义
 *
 * @author zhou
 * @since 2020.09.14
 */
public interface IBlogService {

    String STATE_KEY = "blog_state";

    Result<NULL> save(BlogDTO blogDTO);

    Result<NULL> remove(BlogDTO blogDTO);

    Result<NULL> update(BlogDTO blogDTO);

    Result<BlogDTO> get(BlogDTO blogDTO);

    default Result<List<BlogDTO>> list(BlogDTO blogDTO) {
        return list(blogDTO, null);
    }

    Result<List<BlogDTO>> list(BlogDTO blogDTO, Page page);

    Result<List<TagDTO>> tagList(TagDTO tagDTO);
}
