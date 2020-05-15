package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.po.BlogPO;


/**
 *  数据访问模型
 *
 * @author zhou
 * @since 2020.05.15
 */
@Mapper
@Component
public interface BlogDAO extends BaseDAO<BlogDTO, BlogPO>{
}
