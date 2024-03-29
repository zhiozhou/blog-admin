package priv.zhou.module.system.dict.controller;

import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseViewController;
import priv.zhou.module.system.dict.domain.query.DictQuery;
import priv.zhou.module.system.dict.domain.vo.DictDataVO;
import priv.zhou.module.system.dict.domain.vo.DictVO;
import priv.zhou.module.system.dict.service.IDictService;

/**
 * 字典 视图控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@Controller
@RequestMapping("/system/dict")
public class DictViewController extends BaseViewController {

    private final IDictService dictService;

    public DictViewController(IDictService dictService) {
        super("字典", "system:dict");
        this.dictService = dictService;
    }

    @RequiresPermissions("system:dict:add")
    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new DictVO()
                .setState(0)
                .setDataList(Lists.newArrayList(new DictDataVO()
                        .setType(0)
                        .setDefa(true)
                        .setCode("0")
                        .setLabel("LABEL"))));
        return "system/dict/au";
    }

    @RequiresPermissions("system:dict:update")
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        super.update(model, dictService.getVO(new DictQuery().setId(id)));
        return "system/dict/au";
    }

    @RequiresPermissions("system:dict:detail")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        super.detail(model, dictService.getVO(new DictQuery().setId(id)));
        return "system/dict/detail";
    }

    @RequiresPermissions("system:dict:view")
    @GetMapping
    public String view(Model model) {
        super.list(model);
        return "system/dict/index";
    }
}
