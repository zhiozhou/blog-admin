package priv.zhou.module.system.monitor.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.monitor.domain.dto.OnlineDTO;

import java.util.List;

public interface IOnlineService {

    Result<List<OnlineDTO>> list(OnlineDTO onlineDTO);

    Result<NULL> offline(String id);
}
