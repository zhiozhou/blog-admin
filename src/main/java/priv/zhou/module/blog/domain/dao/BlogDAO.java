package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.blog.domain.po.BlogPO;
import priv.zhou.module.blog.domain.query.BlogQuery;
import priv.zhou.module.blog.domain.vo.BlogTableVO;
import priv.zhou.module.blog.domain.vo.BlogVO;

import java.util.List;


/**
 * 博客 数据访问模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Mapper
@Component
public interface BlogDAO extends BaseDAO<BlogPO, BlogQuery> {

    int removeList(List<Integer> ids);

    BlogVO getVO(BlogQuery query);

    List<BlogTableVO> listTableVO(BlogQuery query);
}
