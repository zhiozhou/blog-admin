package priv.zhou.module.blog.blogType.domain.dao;

import priv.zhou.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.module.blog.blogType.domain.dto.BlogTypeDTO;
import priv.zhou.module.blog.blogType.domain.po.BlogTypePO;

import java.util.List;


/**
 *  数据访问模型
 *
 * @author zhou
 * @since 2020.05.21
 */
@Mapper
@Component
public interface BlogTypeDAO extends BaseDAO<BlogTypeDTO,BlogTypePO>{
}
