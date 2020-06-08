package priv.zhou.module.access.visitor.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;
import priv.zhou.module.access.visitor.service.IVisitorService;

import javax.validation.Valid;

/**
 * 访客 控制层
 *
 * @author zhou
 * @since 2020.06.08
 */
@RestController
@RequestMapping("/access/visitor/rest")
public class VisitorRestController {

    private IVisitorService visitorService;

    public VisitorRestController(IVisitorService visitorService) {
        this.visitorService = visitorService;
    }


    @RequestMapping("/update")
    public OutVO<NULL> update(VisitorDTO visitorDTO) {
        return visitorService.update(visitorDTO);
    }


    @RequestMapping("/block/{id}")
    public OutVO<NULL> block(@PathVariable Integer id) {
        return visitorService.update(new VisitorDTO().setId(id).setState(11));
    }

    @RequestMapping("/unblock/{id}")
    public OutVO<NULL> unblock(@PathVariable Integer id) {
        return visitorService.update(new VisitorDTO().setId(id).setState(0));
    }

    @RequestMapping("/list")
    public OutVO<ListVO<VisitorDTO>> list(VisitorDTO visitorDTO, Page page) {
        return visitorService.list(visitorDTO, page);
    }

}
