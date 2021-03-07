package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import priv.zhou.module.blog.domain.po.TagPO;

import java.util.List;


/**
 * 博客类型 数据访问模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Mapper
@Component
public interface BlogTagDAO {

    void saveList(@Param(value = "tags") List<TagPO> tags, Integer blogId);

    void remove(Integer blogId);

    void deleted(Integer blogId);
}