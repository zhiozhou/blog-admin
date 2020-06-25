package priv.zhou.module.access.comment.service;

import com.alibaba.fastjson.JSONObject;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.param.NULL;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.module.access.comment.domain.dto.CommentDTO;

/**
 * 评论 服务层定义
 *
 * @author zhou
 * @since 2020.06.25
 */
public interface ICommentService {

    OutVO<NULL> reply(CommentDTO commentDTO);

    OutVO<NULL> remove(CommentDTO commentDTO);

    OutVO<NULL> update(CommentDTO commentDTO);

    OutVO<CommentDTO> get(CommentDTO commentDTO);

    OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page);
}
