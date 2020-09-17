package priv.zhou.module.comment.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.comment.domain.dto.CommentDTO;
import priv.zhou.module.comment.service.ICommentService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

import static priv.zhou.module.comment.service.ICommentService.STATE_KEY;

/**
 * 评论 视图控制层
 *
 * @author zhou
 * @since 2020.09.17
 */
@Component
@RequestMapping("/comment")
public class CommentController extends BaseController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        super(new Module("评论", "comment"));
        this.commentService = commentService;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new CommentDTO());

        return "comment/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<CommentDTO> dtoVO = commentService.get(new CommentDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        CommentDTO commentDTO = dtoVO.getData();
        commentDTO.setStateStr(dictService.getData(new DictDTO().setKey(STATE_KEY).setCode(commentDTO.getState())).getData().getLabel());
        super.detail(model, dtoVO.getData());

        return "comment/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);
        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(STATE_KEY)).getData());
        return "comment/list";
    }

    @RequestMapping("/reply/{repliedId}")
    public String reply(Model model, @PathVariable Integer repliedId) {
        super.detail(model, new CommentDTO().setRepliedId(repliedId));
        model.addAttribute(ACTION_KEY, "/rest/reply");
        return "comment/au";
    }

    @RequestMapping("/reply/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        OutVO<CommentDTO> dtoVO = commentService.get(new CommentDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.detail(model, dtoVO.getData());
        model.addAttribute(ACTION_KEY, "/rest/reply/update");
        return "comment/au";
    }

}

