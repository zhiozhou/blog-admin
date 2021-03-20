package priv.zhou.module.blog.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import priv.zhou.module.blog.domain.po.TagPO;
import priv.zhou.module.blog.domain.query.TagQuery;
import priv.zhou.module.blog.domain.vo.TagVO;

import java.util.List;


/**
 * 博客类型 数据访问模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Mapper
@Component
public interface TagDAO {


    int incrSaveList(List<TagPO> tags);

    /**
     * count应用自增
     * @param tagNames 标签名称集合
     * @param delta 自增数量
     */
    int incrList(@Param(value = "tagNames") List<String> tagNames, @Param(value = "delta") Integer delta);

    List<TagPO> list(TagQuery query);

    List<TagVO> listVO(TagQuery query);
}

