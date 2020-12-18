package priv.zhou.module.comment.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Order;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutVOEnum;
import priv.zhou.common.tools.HttpUtil;
import priv.zhou.module.comment.domain.dto.CommentDTO;
import priv.zhou.module.comment.service.ICommentService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 评论 控制层
 *
 * @author zhou
 * @since 2020.09.17
 */
@RestController
@RequestMapping("/comment/rest")
public class CommentRestController {

    private final ICommentService commentService;

    public CommentRestController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return commentService.remove(new CommentDTO().setId(id));
    }

    @RequestMapping("/list")
    public OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page, Order order) {
        return commentService.list(commentDTO, page, order);
    }


    @RequestMapping("/pass/{id}")
    public OutVO<NULL> pass(@PathVariable Integer id) {
        return commentService.update(new CommentDTO().setId(id).setState(1));
    }

    @RequestMapping("/pass/no/{id}")
    public OutVO<NULL> noPass(@PathVariable Integer id) {
        return commentService.update(new CommentDTO().setId(id).setState(9));
    }

    @RequestMapping("/reply/update")
    public OutVO<NULL> updateReply(CommentDTO commentDTO) {
        if (StringUtils.isBlank(commentDTO.getContent())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        return commentService.update(commentDTO);
    }


    @RequestMapping("/reply")
    public OutVO<NULL> reply(HttpServletRequest request, @Valid CommentDTO commentDTO) {
        return commentService.reply(commentDTO
                .setIp(HttpUtil.getIpAddress(request))
                .setUa(HttpUtil.getUserAgent(request)));
    }

    @RequestMapping("/block/{id}")
    public OutVO<NULL> block(@PathVariable Integer id, String reason) {
        return commentService.block(id, reason);
    }

}
