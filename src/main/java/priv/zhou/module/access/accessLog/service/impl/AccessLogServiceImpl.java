package priv.zhou.module.access.accessLog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.module.access.accessLog.domain.dao.AccessLogDAO;
import priv.zhou.module.access.accessLog.domain.dto.AccessLogDTO;
import priv.zhou.module.access.accessLog.domain.po.AccessLogPO;
import priv.zhou.module.access.accessLog.service.IAccessLogService;

import java.util.List;


/**
 * 访问日志 服务层实现
 *
 * @author zhou
 * @since 2020.06.08
 */
@Service
public class AccessLogServiceImpl implements IAccessLogService {

    private final AccessLogDAO accessLogDAO;

    public AccessLogServiceImpl(AccessLogDAO accessLogDAO) {
        this.accessLogDAO = accessLogDAO;
    }

    @Override
    public OutVO<AccessLogDTO> get(AccessLogDTO accessLogDTO) {
        AccessLogPO accessLogPO = accessLogDAO.get(accessLogDTO);
        if (null == accessLogPO) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        }
        return OutVO.success(DTO.ofPO(accessLogPO, AccessLogDTO::new));
    }

    @Override
    public OutVO<ListVO<AccessLogDTO>> list(AccessLogDTO accessLogDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<AccessLogPO> poList = accessLogDAO.list(accessLogDTO);
        PageInfo<AccessLogPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, AccessLogDTO::new), pageInfo.getTotal());
    }
}
