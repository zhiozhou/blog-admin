package priv.zhou.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import priv.zhou.common.domain.Module;
import priv.zhou.common.param.AppProperties;
import priv.zhou.module.system.dict.service.IDictService;

import java.util.HashMap;

public class BaseController {

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected IDictService dictService;

    protected Module module;

    protected final String MODULE_KEY = "_module";

    protected final String VO_KEY = "_vo";

    protected final String ACTION_KEY = "_action";

    protected final String ADD_ACTION = "/rest/save";

    protected final String UPDATE_ACTION = "/rest/update";

    protected final String UPLOAD_PARAM_KEY = "_upload";

    protected final String NOT_FOUNT = "404";

    public BaseController(Module module) {
        this.module = module;
    }

    /**
     * 新增页填充 提交地址，vo对象
     */
    protected void add(Model model, Object vo) {
        model.addAttribute(VO_KEY, vo);
        model.addAttribute(MODULE_KEY, module);
        model.addAttribute(ACTION_KEY, ADD_ACTION);
    }

    /**
     * 修改页填充 提交地址，vo对象
     */
    protected void update(Model model, Object vo) {
        model.addAttribute(VO_KEY, vo);
        model.addAttribute(MODULE_KEY, module);
        model.addAttribute(ACTION_KEY, UPDATE_ACTION);
    }

    /**
     * 详情页填充：vo对象
     */
    protected void detail(Model model, Object vo) {
        model.addAttribute(VO_KEY, vo);
        model.addAttribute(MODULE_KEY, module);
    }


    /**
     * 列表页填充：模块对象
     */
    protected String list(Model model) {
        model.addAttribute(MODULE_KEY, module);
        return "";
    }


    /**
     * 上传功能参数：{url，prefix}
     */
    protected void upload(Model model) {
        model.addAttribute(UPLOAD_PARAM_KEY, new HashMap<String, Object>() {{
            put("url", appProperties.getFileService());
            put("prefix", appProperties.getFileUploadPrefix());
        }});
    }

}
