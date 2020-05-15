package priv.zhou.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import priv.zhou.common.domain.Module;
import priv.zhou.common.param.AppProperties;
import priv.zhou.module.system.dict.service.IDictService;

public class BaseController {

    protected String MODULE_KEY = "m";

    protected String GATE_KEY = "g";

    protected String OBJECT_KEY = "o";

    protected String ACTION_KEY = "action";

    protected String ADD_ACTION = "/rest/save";

    protected String UPDATE_ACTION = "/rest/update";

    protected String NOT_FOUNT = "404";

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected IDictService dictService;


    /**
     * 新增页填充 gate，提交后缀，dto对象
     */
    protected void fillAdd(Model model, Object object) {
        fillDetail(model, object);
        model.addAttribute(ACTION_KEY, ADD_ACTION);
    }

    /**
     * 修改页填充 gate，提交后缀，dto对象
     */
    protected void fillUpdate(Model model, Object object) {
        fillDetail(model, object);
        model.addAttribute(ACTION_KEY, UPDATE_ACTION);
    }

    /**
     * 详情页添新宠 gate,dto对象
     */
    protected void fillDetail(Model model, Object object) {
        model.addAttribute(GATE_KEY, appProperties.getGate());
        model.addAttribute(OBJECT_KEY, object);
    }


    /**
     * 列表页填充 模块对象，gate
     */
    protected void fillList(Model model, Module module) {
        model.addAttribute(MODULE_KEY, module);
        model.addAttribute(GATE_KEY, appProperties.getGate());
    }

}
