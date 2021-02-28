package priv.zhou.module.system.monitor.controller;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.constant.NULL;
import priv.zhou.module.system.monitor.domain.query.SessionQuery;
import priv.zhou.module.system.monitor.domain.vo.SessionVO;
import priv.zhou.module.system.monitor.service.ISessionService;

import java.util.List;

/**
 * 监控 接口控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/monitor/rest")
public class MonitorRestController {

    private final ISessionService sessionService;

    @RequiresPermissions("system:monitor:view")
    @RequestMapping("/list")
    public Result<List<SessionVO>> login(SessionQuery query) {
        return sessionService.list(query);
    }

    @RequiresPermissions("system:monitor:offline")
    @RequestMapping("/offline/{id}")
    public Result<NULL> offline(@PathVariable String id) {
        return sessionService.offline(id);
    }
}
