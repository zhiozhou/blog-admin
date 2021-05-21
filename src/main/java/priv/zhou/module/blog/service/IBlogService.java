package priv.zhou.module.blog.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.query.BlogQuery;
import priv.zhou.module.blog.domain.query.TagQuery;
import priv.zhou.module.blog.domain.vo.BlogTableVO;
import priv.zhou.module.blog.domain.vo.BlogVO;
import priv.zhou.module.blog.domain.vo.TagVO;

import java.util.List;

/**
 * 博客 服务层定义
 *
 * @author zhou
 * @since 2020.09.14
 */
public interface IBlogService {

    String STATE_DICT_KEY = "blog_state";

    Result<NULL> save(BlogDTO blogDTO);

    Result<NULL> remove(Integer id);

    Result<NULL> update(BlogDTO blogDTO);

    BlogVO getVO(BlogQuery query);

    List<BlogTableVO> listTableVO(BlogQuery query, Page page);

    List<TagVO> listTag(TagQuery query);
}
