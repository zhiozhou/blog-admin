package priv.zhou.module.access.comment.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.access.comment.domain.dao.CommentDAO;
import priv.zhou.module.access.comment.domain.dto.CommentDTO;
import priv.zhou.module.access.comment.domain.po.CommentPO;
import priv.zhou.module.access.comment.service.ICommentService;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;
import priv.zhou.module.access.visitor.domain.po.VisitorPO;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.CONSTANT.ZHOU_VISITOR_ID;

import java.util.List;


/**
 * 评论 服务层实现
 *
 * @author zhou
 * @since 2020.06.25
 */
@Service
public class CommentServiceImpl implements ICommentService {

    private final CommentDAO commentDAO;

    public CommentServiceImpl(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    @Override
    public OutVO<NULL> reply(CommentDTO commentDTO) {
        if(null == commentDTO.getRepliedComment() || null == commentDTO.getRepliedComment().getId()){
            return  OutVO.fail(OutVOEnum.FAIL_PARAM);
        }

        CommentPO targetPO = commentDAO.get(commentDTO.getRepliedComment());
        if (null == targetPO || targetPO.getState().equals(0)) {
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        }

        CommentPO commentPO = commentDTO.toPO()
                .setBlog(targetPO.getBlog())
                .setTopicId(targetPO.getTopicId())
                .setToVisitor(targetPO.getFromVisitor())
                .setFromVisitor(new VisitorPO().setId(ZHOU_VISITOR_ID))
                .setState(1);
        return commentDAO.save(commentPO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    public OutVO<NULL> remove(CommentDTO commentDTO) {
        if (isNull(commentDTO.getId())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        commentDAO.remove(commentDTO);
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(CommentDTO commentDTO) {
        CommentPO commentPO = commentDTO.toPO();
        return  commentDAO.update(commentPO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }


    @Override
    public OutVO<CommentDTO> get(CommentDTO commentDTO) {

        CommentPO commentPO = commentDAO.get(commentDTO);
        return OutVO.success(new CommentDTO(commentPO));
    }

    @Override
    public OutVO<ListVO<CommentDTO>> list(CommentDTO commentDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<CommentPO> poList = commentDAO.list(commentDTO);
        PageInfo<CommentPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList,CommentDTO::new),  pageInfo.getTotal());
    }
}
