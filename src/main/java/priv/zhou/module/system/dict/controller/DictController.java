package priv.zhou.module.system.dict.controller;

import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.Result;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.service.IDictService;

/**
 * 字典 视图控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@Component
@RequestMapping("/system/dict")
public class DictController extends BaseController {

    private final IDictService dictService;


    public DictController(IDictService dictService) {
        super(new Module("字典", "system:dict"));
        this.dictService = dictService;
    }

    @RequiresPermissions("system:dict:add")
    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new DictDTO().setState(0).setDataList(Lists.newArrayList(new DictDataDTO().setType(0).setTop(0))));
        return "system/dict/au";
    }

    @RequiresPermissions("system:dict:update")
    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        Result<DictDTO> dtoVO = dictService.get(new DictDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());
        return "system/dict/au";
    }

    @RequiresPermissions("system:dict:detail")
    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        Result<DictDTO> dtoVO = dictService.get(new DictDTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.detail(model, dtoVO.getData());
        return "system/dict/detail";
    }

    @RequiresPermissions("system:dict:list")
    @RequestMapping("/list")
    public String login(Model model) {
        super.list(model);
        return "system/dict/list";
    }
}
