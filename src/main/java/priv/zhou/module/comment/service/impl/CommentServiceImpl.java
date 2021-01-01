package priv.zhou.module.comment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Order;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.OutVOEnum;
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
    public OutVO<NULL> remove(CommentDTO commentDTO) {
        if (null == commentDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return commentDAO.remove(commentDTO)<1?
                OutVO.fail(OutVOEnum.FAIL_OPERATION):
                OutVO.success();
    }

    @Override
    public OutVO<NULL> update(CommentDTO commentDTO) {
        if (null == commentDTO.getId()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        CommentPO commentPO = commentDTO.toPO();
        return commentDAO.update(commentPO) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }


    @Override
    public OutVO<CommentDTO> get(CommentDTO commentDTO) {
        CommentPO commentPO = commentDAO.get(commentDTO);
        return OutVO.success(new CommentDTO(commentPO));
    }

    @Override
    public OutVO<TableVO<CommentDTO>> list(CommentDTO commentDTO, Page page, Order order) {
        startPage(page);
        List<CommentPO> poList = commentDAO.list(commentDTO, order);
        PageInfo<CommentPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, CommentDTO::new), pageInfo.getTotal());
    }

    @Override
    public OutVO<NULL> reply(CommentDTO commentDTO) {
        CommentPO targetPO = commentDAO.get(new CommentDTO().setId(commentDTO.getRepliedId()));
        if (null == targetPO || targetPO.getState().equals(0)) {
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        }

        CommentPO commentPO = commentDTO.toPO()
                .setState(1)
                .setBlog(targetPO.getBlog())
                .setToVid(targetPO.getFromVisitor().getId())
                .setFromVisitor(new VisitorPO().setId(ZHOU_VISITOR_ID))
                .setTopicId(targetPO.getTopicId().equals(0) ? targetPO.getId() : targetPO.getTopicId());
        return commentDAO.save(commentPO) > 0 ?
                OutVO.success() :
                OutVO.fail(OutVOEnum.FAIL_OPERATION);
    }

    @Override
    @Transactional
    public OutVO<NULL> block(Integer id, String reason) {
        CommentPO commentPO = commentDAO.get(new CommentDTO().setId(id));
        if (null == commentPO) {
            return OutVO.fail(OutVOEnum.FAIL_PARAM);
        } else if (commentDAO.update(commentPO.setState(10)) < 0) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        } else if (commentDAO.blockIP(commentPO.getIp()) < 1 ||
                blockService.save(new BlockDTO()
                        .setType(0)
                        .setIp(commentPO.getIp())
                        .setReason(reason)
                        .setRemark(commentPO.getId().toString())).isFail()) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        }
        return OutVO.success();
    }
}
