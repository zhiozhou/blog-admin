package priv.zhou.module.comment.service;

import priv.zhou.common.domain.dto.Order;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.comment.domain.dto.CommentDTO;

/**
 * 评论 服务层定义
 *
 * @author zhou
 * @since 2020.09.17
 */
public interface ICommentService {


    String STATE_KEY = "comment_state";

    OutVO<NULL> remove(CommentDTO commentDTO);

    OutVO<NULL> update(CommentDTO commentDTO);

    OutVO<CommentDTO> get(CommentDTO commentDTO);

    OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page, Order order);

    OutVO<NULL> reply(CommentDTO commentDTO);
}
