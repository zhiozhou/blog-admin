package priv.zhou.module.comment.domain.dao;

import priv.zhou.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.module.comment.domain.dto.CommentDTO;
import priv.zhou.module.comment.domain.po.CommentPO;

import java.util.List;


/**
 * 评论 数据访问模型
 *
 * @author zhou
 * @since 2020.09.17
 */
@Mapper
@Component
public interface CommentDAO extends BaseDAO<CommentDTO,CommentPO>{
}
