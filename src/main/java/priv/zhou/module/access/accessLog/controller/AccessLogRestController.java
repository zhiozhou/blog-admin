package priv.zhou.module.access.accessLog.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.access.accessLog.domain.dto.AccessLogDTO;
import priv.zhou.module.access.accessLog.service.IAccessLogService;
import priv.zhou.module.blog.blog.domain.dto.BlogDTO;

/**
 * 访问日志 控制层
 *
 * @author zhou
 * @since 2020.06.08
 */
@RestController
@RequestMapping("/access/log/rest")
public class AccessLogRestController {

    private final IAccessLogService accessLogService;

    public AccessLogRestController(IAccessLogService accessLogService) {
        this.accessLogService = accessLogService;
    }


    @RequestMapping("/list")
    public OutVO<ListVO<AccessLogDTO>> list(AccessLogDTO accessLogDTO, Page page) {
        return accessLogService.list(accessLogDTO, page);
    }

}
