package priv.zhou.common.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import priv.zhou.common.domain.Module;
import priv.zhou.common.param.AppProperties;
import priv.zhou.module.system.dict.service.IDictService;

public class BaseController {

    protected String MODULE_KEY = "m";

    protected String OBJECT_KEY = "o";

    protected String ACTION_KEY = "action";

    protected String ADD_ACTION = "/rest/save";

    protected String UPDATE_ACTION = "/rest/update";

    protected String NOT_FOUNT = "404";

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected IDictService dictService;


    protected void supplyUpload(Model model)  {
        model.addAttribute("uploadURL", appProperties.getFileService());
        model.addAttribute("uploadPrefix", appProperties.getFileUploadPrefix());
    }

    /**
     * 新增页填充 gate，提交后缀，dto对象
     */
    protected void supplyAdd(Model model, Object object) {
        supplyDetail(model, object);
        model.addAttribute(ACTION_KEY, ADD_ACTION);
    }

    /**
     * 修改页填充 gate，提交后缀，dto对象
     */
    protected void supplyUpdate(Model model, Object object) {
        supplyDetail(model, object);
        model.addAttribute(ACTION_KEY, UPDATE_ACTION);
    }

    /**
     * 详情页添新宠 gate,dto对象
     */
    protected void supplyDetail(Model model, Object object) {
        model.addAttribute(OBJECT_KEY, object);
    }


    /**
     * 列表页填充 模块对象，gate
     */
    protected void supplyList(Model model, Module module) {
        model.addAttribute(MODULE_KEY, module);
    }

}
