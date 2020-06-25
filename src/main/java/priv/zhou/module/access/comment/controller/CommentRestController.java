package priv.zhou.module.access.comment.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.access.comment.domain.dto.CommentDTO;
import priv.zhou.module.access.comment.service.ICommentService;

import javax.validation.Valid;

/**
 * 评论 控制层
 *
 * @author zhou
 * @since 2020.06.25
 */
@RestController
@RequestMapping("/access/comment/rest")
public class CommentRestController {

    private final ICommentService commentService;

    public CommentRestController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/reply")
    public OutVO<NULL> reply(@Valid CommentDTO commentDTO) {
        return commentService.reply(commentDTO);
    }

    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return commentService.remove(new CommentDTO().setId(id));
    }

    @RequestMapping("/update")
    public OutVO<NULL> update(CommentDTO commentDTO) {
        return commentService.update(commentDTO);
    }


    @RequestMapping("/list")
    public OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page) {
        return commentService.list(commentDTO, page);
    }


}
