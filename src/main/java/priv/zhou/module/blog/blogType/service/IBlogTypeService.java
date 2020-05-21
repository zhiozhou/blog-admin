package priv.zhou.module.blog.blogType.service;

import com.alibaba.fastjson.JSONObject;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.param.NULL;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;

/**
 *  服务层定义
 *
 * @author zhou
 * @since 2020.05.21
 */
public interface IBlogTypeService {

    OutVO<NULL> save(BlogTypeDTO blogTypeDTO);

    OutVO<NULL> remove(BlogTypeDTO blogTypeDTO);

    OutVO<NULL> update(BlogTypeDTO blogTypeDTO);

    OutVO<BlogTypeDTO> get(BlogTypeDTO blogTypeDTO);

    OutVO<ListVO<BlogTypeDTO>> list(BlogTypeDTO blogTypeDTO, Page page);
}
