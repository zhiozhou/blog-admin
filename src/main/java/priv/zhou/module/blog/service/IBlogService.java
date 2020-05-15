package priv.zhou.module.blog.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.blog.domain.dto.BlogDTO;

/**
 *  服务层定义
 *
 * @author zhou
 * @since 2020.05.15
 */
public interface IBlogService {

    OutVO<NULL> save(BlogDTO blogDTO);

    OutVO<NULL> remove(BlogDTO blogDTO);

    OutVO<NULL> update(BlogDTO blogDTO);

    OutVO<BlogDTO> get(BlogDTO blogDTO);

    OutVO<ListVO<BlogDTO>> list(BlogDTO blogDTO, Page page);

    OutVO<Integer> count(BlogDTO blogDTO);
}
