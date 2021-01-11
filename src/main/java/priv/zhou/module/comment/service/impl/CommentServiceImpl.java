package priv.zhou.module.comment.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Order;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.block.domain.dto.BlockDTO;
import priv.zhou.module.block.service.IBlockService;
import priv.zhou.module.comment.domain.dao.CommentDAO;
import priv.zhou.module.comment.domain.dto.CommentDTO;
import priv.zhou.module.comment.domain.po.CommentPO;
import priv.zhou.module.comment.service.ICommentService;
import priv.zhou.module.visitor.domain.po.VisitorPO;

import java.util.List;

import static priv.zhou.common.misc.Const.ZHOU_VISITOR_ID;


/**
 * 评论 服务层实现
 *
 * @author zhou
 * @since 2020.09.17
 */
@Service
public class CommentServiceImpl extends BaseService implements ICommentService {

    private final CommentDAO commentDAO;

    private final IBlockService blockService;

    public CommentServiceImpl(CommentDAO commentDAO, IBlockService blockService) {
        this.commentDAO = commentDAO;
        this.blockService = blockService;
    }

    @Override
    public Result<NULL> remove(CommentDTO commentDTO) {
        if (null == commentDTO.getId()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }

        return commentDAO.remove(commentDTO) < 1 ?
                Result.fail(ResultEnum.FAIL_OPERATION) :
                Result.success();
    }

    @Override
    public Result<NULL> update(CommentDTO commentDTO) {
        if (null == commentDTO.getId()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }

        CommentPO commentPO = commentDTO.toPO();
        return commentDAO.update(commentPO) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.FAIL_OPERATION);

    }


    @Override
    public Result<CommentDTO> get(CommentDTO commentDTO) {
        CommentPO commentPO = commentDAO.get(commentDTO);
        return Result.success(new CommentDTO(commentPO));
    }

    @Override
    public Result<List<CommentDTO>> list(CommentDTO commentDTO, Page page, Order order) {
        startPage(page);
        return Result.success(DTO.ofPO(commentDAO.list(commentDTO, order), CommentDTO::new));
    }

    @Override
    public Result<NULL> reply(CommentDTO commentDTO) {
        CommentPO targetPO = commentDAO.get(new CommentDTO().setId(commentDTO.getRepliedId()));
        if (null == targetPO || targetPO.getState().equals(0)) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        }

        CommentPO commentPO = commentDTO.toPO()
                .setState(1)
                .setBlog(targetPO.getBlog())
                .setToVid(targetPO.getFromVisitor().getId())
                .setFromVisitor(new VisitorPO().setId(ZHOU_VISITOR_ID))
                .setTopicId(targetPO.getTopicId().equals(0) ? targetPO.getId() : targetPO.getTopicId());
        return commentDAO.save(commentPO) > 0 ?
                Result.success() :
                Result.fail(ResultEnum.FAIL_OPERATION);
    }

    @Override
    @Transactional
    public Result<NULL> block(Integer id, String reason) {
        CommentPO commentPO = commentDAO.get(new CommentDTO().setId(id));
        if (null == commentPO) {
            return Result.fail(ResultEnum.FAIL_PARAM);
        } else if (commentDAO.update(commentPO.setState(10)) < 0) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        } else if (commentDAO.blockIP(commentPO.getIp()) < 1 ||
                blockService.save(new BlockDTO()
                        .setType(0)
                        .setIp(commentPO.getIp())
                        .setReason(reason)
                        .setRemark(commentPO.getId().toString())).isFail()) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        }
        return Result.success();
    }
}
