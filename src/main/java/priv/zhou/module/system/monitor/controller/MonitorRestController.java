package priv.zhou.module.system.monitor.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.system.monitor.domain.dto.OnlineDTO;
import priv.zhou.module.system.monitor.service.IOnlineService;

import java.util.List;

/**
 * 监控 接口控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@RestController
@RequestMapping("/system/monitor/rest")
public class MonitorRestController {

    private final IOnlineService onlineService;

    public MonitorRestController(IOnlineService onlineService) {
        this.onlineService = onlineService;
    }

    @RequiresPermissions("system:monitor:list")
    @RequestMapping("/list")
    public OutVO<List<OnlineDTO>> login(OnlineDTO onlineDTO) {

        return onlineService.list(onlineDTO);
    }

    @RequiresPermissions("system:monitor:forceOff")
    @RequestMapping("/force/off")
    public OutVO<NULL> forceOff(OnlineDTO onlineDTO) {
        return onlineService.forceOff(onlineDTO);
    }
}
