package priv.zhou.module.access.accessLog.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.access.accessLog.domain.dto.AccessLogDTO;
import priv.zhou.module.access.accessLog.service.IAccessLogService;

/**
 * 访问日志 视图控制层
 *
 * @author zhou
 * @since 2020.06.08
 */
@Component
@RequestMapping("/access/log")
public class AccessLogController extends BaseController {

    private final IAccessLogService accessLogService;


    public AccessLogController(IAccessLogService accessLogService) {
        super(new Module("访问日志", "access:log"));
        this.accessLogService = accessLogService;
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<AccessLogDTO> dtoVO = accessLogService.get(new AccessLogDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.detail(model, dtoVO.getData());
        return "access/log/detail";
    }


    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);
        return "access/log/list";
    }
}
