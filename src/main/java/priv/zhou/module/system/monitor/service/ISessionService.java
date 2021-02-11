package priv.zhou.module.system.monitor.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.constant.NULL;
import priv.zhou.module.system.monitor.domain.query.SessionQuery;
import priv.zhou.module.system.monitor.domain.vo.SessionVO;

import java.util.List;

public interface ISessionService {

    Result<List<SessionVO>> list(SessionQuery query);

    Result<NULL> offline(String username);
}
