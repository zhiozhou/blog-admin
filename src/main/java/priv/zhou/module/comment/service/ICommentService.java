package priv.zhou.module.comment.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Order;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.constant.NULL;
import priv.zhou.module.comment.domain.dto.CommentDTO;

import java.util.List;

/**
 * 评论 服务层定义
 *
 * @author zhou
 * @since 2020.09.17
 */
public interface ICommentService {


    String STATE_KEY = "comment_state";

    String BLOCK_TYPE_KEY = "comment_block_type";

    Result<NULL> remove(CommentDTO commentDTO);

    Result<NULL> update(CommentDTO commentDTO);

    Result<CommentDTO> get(CommentDTO commentDTO);

    default Result<List<CommentDTO>> list(CommentDTO commentDTO, Order order) {
        return list(commentDTO, null, order);
    }

    Result<List<CommentDTO>> list(CommentDTO commentDTO, Page page, Order order);

    Result<NULL> reply(CommentDTO commentDTO);

    Result<NULL> block(Integer id, String reason);
}
