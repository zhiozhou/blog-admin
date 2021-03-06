package priv.zhou.module.comment.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Order;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.enums.ResultEnum;
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

    @PostMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return commentService.remove(new CommentDTO().setId(id));
    }

    @PostMapping("/list")
    public Result<TableVO<CommentDTO>> list(CommentDTO commentDTO, Page page, Order order) {
        return Result.table(commentService.list(commentDTO, page, order));
    }


    @PostMapping("/pass/{id}")
    public Result<NULL> pass(@PathVariable Integer id) {
        return commentService.update(new CommentDTO().setId(id).setState(1));
    }

    @PostMapping("/pass/no/{id}")
    public Result<NULL> noPass(@PathVariable Integer id) {
        return commentService.update(new CommentDTO().setId(id).setState(9));
    }

    @PostMapping("/reply/update")
    public Result<NULL> updateReply(CommentDTO commentDTO) {
        if (StringUtils.isBlank(commentDTO.getContent())) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }
        return commentService.update(commentDTO);
    }


    @PostMapping("/reply")
    public Result<NULL> reply(HttpServletRequest request, @Valid CommentDTO commentDTO) {
        return commentService.reply(commentDTO
                .setIp(HttpUtil.getIpAddress(request))
                .setUa(HttpUtil.getUserAgent(request)));
    }

    @PostMapping("/block/{id}")
    public Result<NULL> block(@PathVariable Integer id, String reason) {
        return commentService.block(id, reason);
    }

}
