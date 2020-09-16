package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.blog.domain.dto.TagDTO;
import priv.zhou.module.blog.domain.po.TagPO;

import java.util.Set;


/**
 * 博客类型 数据访问模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Mapper
@Component
public interface TagDAO extends BaseDAO<TagDTO, TagPO> {

    void saveMap(@Param("tags") Set<TagPO> tags, @Param("blogId") Integer blogId);

    void removeMap(@Param("tags") Set<TagPO> tags, @Param("blogId") Integer blogId);
}
