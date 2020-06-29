package priv.zhou.module.access.comment.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.controller.BaseController;
import priv.zhou.module.access.comment.domain.dto.CommentDTO;
import priv.zhou.module.access.comment.service.ICommentService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

/**
 * 评论 视图控制层
 *
 * @author zhou
 * @since 2020.06.25
 */
@Component
@RequestMapping("/access/comment")
public class CommentController extends BaseController {

    private final ICommentService commentService;

    private final String STATE_KEY = "comment_state";

    private final Module module = new Module("评论","access:comment");

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/reply/{repliedId}")
    public String add(Model model, @PathVariable Integer repliedId) {

        fillDetail(model, new CommentDTO()
                .setRepliedComment(new CommentDTO().setId(repliedId)));
        model.addAttribute(ACTION_KEY, "/rest/reply");
        return "access/comment/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        OutVO<CommentDTO> dtoVO = commentService.get(new CommentDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        fillUpdate(model, dtoVO.getData());
        return "access/comment/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<CommentDTO> dtoVO = commentService.get(new CommentDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        CommentDTO commentDTO = dtoVO.getData();
        commentDTO.setStateStr(dictService.getData(new DictDTO().setKey(STATE_KEY).setCode(commentDTO.getState())).getData().getLabel());
        fillDetail(model, dtoVO.getData());
        return "access/comment/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);

        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(STATE_KEY)).getData());
        return "access/comment/list";
    }
}