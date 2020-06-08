package priv.zhou.module.access.visitor.service.impl;

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
import priv.zhou.module.access.visitor.domain.dao.VisitorDAO;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;
import priv.zhou.module.access.visitor.domain.po.VisitorPO;
import priv.zhou.module.access.visitor.service.IVisitorService;
import static java.util.Objects.isNull;
import java.util.List;


/**
 * 访客 服务层实现
 *
 * @author zhou
 * @since 2020.06.08
 */
@Service
public class VisitorServiceImpl implements IVisitorService {

    private final VisitorDAO visitorDAO;

    public VisitorServiceImpl(VisitorDAO visitorDAO) {
        this.visitorDAO = visitorDAO;
    }

    @Override
    public OutVO<NULL> update(VisitorDTO visitorDTO) {

        VisitorPO visitorPO = visitorDTO.toPO();
        return  visitorDAO.update(visitorPO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }


    @Override
    public OutVO<VisitorDTO> get(VisitorDTO visitorDTO) {
        VisitorPO visitorPO = visitorDAO.get(visitorDTO);
        return OutVO.success(new VisitorDTO(visitorPO));
    }

    @Override
    public OutVO<ListVO<VisitorDTO>> list(VisitorDTO visitorDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<VisitorPO> poList = visitorDAO.list(visitorDTO);
        PageInfo<VisitorPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList,VisitorDTO::new),  pageInfo.getTotal());
    }
}
