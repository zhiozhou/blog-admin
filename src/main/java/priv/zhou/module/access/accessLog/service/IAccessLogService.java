package priv.zhou.module.access.accessLog.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.access.accessLog.domain.dto.AccessLogDTO;

/**
 * 访问日志 服务层定义
 *
 * @author zhou
 * @since 2020.06.08
 */
public interface IAccessLogService {

    OutVO<AccessLogDTO> get(AccessLogDTO accessLogDTO);

    OutVO<ListVO<AccessLogDTO>> list(AccessLogDTO accessLogDTO, Page page);
}
