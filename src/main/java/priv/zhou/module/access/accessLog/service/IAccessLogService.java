package priv.zhou.module.access.accessLog.service;

import com.alibaba.fastjson.JSONObject;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.param.NULL;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.module.access.accessLog.domain.dto.AccessLogDTO;

/**
 * 访问日志 服务层定义
 *
 * @author zhou
 * @since 2020.06.08
 */
public interface IAccessLogService {

    OutVO<ListVO<AccessLogDTO>> list(AccessLogDTO accessLogDTO, Page page);

    OutVO<Integer> count(AccessLogDTO accessLogDTO);
}
