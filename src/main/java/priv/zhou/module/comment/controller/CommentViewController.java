package priv.zhou.module.comment.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseViewController;
import priv.zhou.common.domain.Result;
import priv.zhou.module.comment.domain.dto.CommentDTO;
import priv.zhou.module.comment.service.ICommentService;

import static priv.zhou.module.comment.service.ICommentService.BLOCK_TYPE_KEY;
import static priv.zhou.module.comment.service.ICommentService.STATE_KEY;

/**
 * 评论 视图控制层
 *
 * @author zhou
 * @since 2020.09.17
 */
@Component
@RequestMapping("/comment")
public class CommentViewController extends BaseViewController {

    private final ICommentService commentService;

    public CommentViewController(ICommentService commentService) {
        super("评论", "comment");
        this.commentService = commentService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new CommentDTO());

        return "comment/au";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        Result<CommentDTO> dtoVO = commentService.get(new CommentDTO().setId(id));
        if (dtoVO.isFail()) {
            return error();
        }
        CommentDTO commentDTO = dtoVO.getData();
        commentDTO.setStateStr(dictService.getLabel(STATE_KEY, commentDTO.getState()));
        super.detail(model, dtoVO.getData());

        return "comment/detail";
    }

    @GetMapping("/reply/{repliedId}")
    public String reply(Model model, @PathVariable Integer repliedId) {
        super.detail(model, new CommentDTO().setRepliedId(repliedId));
        model.addAttribute(ACTION_KEY, "/rest/reply");
        return "comment/au";
    }

    @GetMapping("/reply/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        Result<CommentDTO> dtoVO = commentService.get(new CommentDTO().setId(id));
        if (dtoVO.isFail()) {
            return error();
        }
        super.detail(model, dtoVO.getData());
        model.addAttribute(ACTION_KEY, "/rest/reply/update");
        return "comment/au";
    }

    @GetMapping("/block/{id}")
    public String block(Model model, @PathVariable Integer id) {

        model.addAttribute("typeList", dictService.listDataVO(BLOCK_TYPE_KEY, DICT_NORM_TYPE));
        model.addAttribute(ACTION_KEY, "/rest/block/" + id);
        return "comment/block";
    }


    @GetMapping
    public String view(Model model) {
        super.list(model);
        model.addAttribute("stateMap", dictService.mapDataVO(STATE_KEY, DICT_NORM_TYPE));
        return "comment/index";
    }
}

