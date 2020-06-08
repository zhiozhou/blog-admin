package priv.zhou.module.access.visitor.service;

import com.alibaba.fastjson.JSONObject;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.param.NULL;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;

/**
 * 访客 服务层定义
 *
 * @author zhou
 * @since 2020.06.08
 */
public interface IVisitorService {

    OutVO<NULL> update(VisitorDTO visitorDTO);

    OutVO<VisitorDTO> get(VisitorDTO visitorDTO);

    OutVO<ListVO<VisitorDTO>> list(VisitorDTO visitorDTO, Page page);
}
