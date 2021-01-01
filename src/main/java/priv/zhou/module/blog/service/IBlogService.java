package priv.zhou.module.blog.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.TableVO;
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

    OutVO<NULL> save(BlogDTO blogDTO);

    OutVO<NULL> remove(BlogDTO blogDTO);

    OutVO<NULL> update(BlogDTO blogDTO);

    OutVO<BlogDTO> get(BlogDTO blogDTO);

    default OutVO<TableVO<BlogDTO>> list(BlogDTO blogDTO) {
        return list(blogDTO, null);
    }

    OutVO<TableVO<BlogDTO>> list(BlogDTO blogDTO, Page page);

    OutVO<List<TagDTO>> tagList(TagDTO tagDTO);
}
