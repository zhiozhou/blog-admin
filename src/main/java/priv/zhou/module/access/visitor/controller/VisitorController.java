package priv.zhou.module.access.visitor.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;
import priv.zhou.module.access.visitor.service.IVisitorService;
import priv.zhou.module.system.dict.domain.dto.DictDTO;

/**
 * 访客 视图控制层
 *
 * @author zhou
 * @since 2020.06.08
 */
@Component
@RequestMapping("/access/visitor")
public class VisitorController extends BaseController {

    private final IVisitorService visitorService;

    private final String STATE_KEY = "visitor_state";

    public VisitorController(IVisitorService visitorService) {
        super(new Module("访客", "access:visitor"));
        this.visitorService = visitorService;
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<VisitorDTO> dtoVO = visitorService.get(new VisitorDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());

        model.addAttribute("stateList", dictService.dataList(new DictDTO().setKey(STATE_KEY)).getData());
        return "access/visitor/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<VisitorDTO> dtoVO = visitorService.get(new VisitorDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        VisitorDTO visitorDTO = dtoVO.getData();
        visitorDTO.setStateStr(dictService.getData(new DictDTO().setKey(STATE_KEY).setCode(visitorDTO.getState())).getData().getLabel());
        super.detail(model, dtoVO.getData());
        return "access/visitor/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);

        model.addAttribute("stateMap", dictService.dataMap(new DictDTO().setKey(STATE_KEY)).getData());
        return "access/visitor/list";
    }
}
