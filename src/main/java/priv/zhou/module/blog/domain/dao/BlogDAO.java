package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO1;
import priv.zhou.module.blog.domain.dto.BlogDTO;
import priv.zhou.module.blog.domain.po.BlogPO;


/**
 * 博客 数据访问模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Mapper
@Component
public interface BlogDAO extends BaseDAO1<BlogDTO, BlogPO> {
}
