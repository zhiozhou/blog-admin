package priv.zhou.module.system.monitor.service;

import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.monitor.domain.dto.OnlineDTO;

import java.util.List;

public interface IOnlineService {

    OutVO<List<OnlineDTO>> list(OnlineDTO onlineDTO);

    OutVO<NULL> offline(String id);
}
