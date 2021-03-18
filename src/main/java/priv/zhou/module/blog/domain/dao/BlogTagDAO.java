package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import priv.zhou.module.blog.domain.po.TagPO;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 博客类型 数据访问模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Mapper
@Component
public interface BlogTagDAO {

    int listSave(@Param(value = "tags") List<TagPO> tags, Integer blogId);

    int remove(Integer blogId);

    int deleted(Integer blogId);
}
