package priv.zhou.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import priv.zhou.common.domain.Module;
import priv.zhou.common.enums.PageEnum;
import priv.zhou.common.properties.AppProperties;
import priv.zhou.framework.exception.PageException;
import priv.zhou.module.system.dict.service.IDictService;

import java.util.HashMap;

/**
 * 基础视图控制层
 *
 * @author zhou
 * @since 0.1.0
 */
public class BaseViewController {

    protected Module module;

    protected final Integer DICT_NORM_TYPE = 0;

    protected final String MODULE_KEY = "_module";

    protected final String VO_KEY = "_vo";

    protected final String ACTION_KEY = "_action";

    protected final String ACTION_TYPE_KEY = "_isAdd";

    protected final String ADD_PATH = "/rest/save";

    protected final String UPDATE_PATH = "/rest/update";

    protected final String UPLOAD_PARAM_KEY = "_upload";

    @Autowired
    protected AppProperties appProperties;

    @Autowired
    protected IDictService dictService;

    public BaseViewController(String name, String permissionPrefix) {
        this.module = Module.build(name, permissionPrefix);
    }

    /**
     * 新增页填充 提交地址，vo对象
     */
    protected void add(Model model, Object vo) {
        model.addAttribute(VO_KEY, vo);
        model.addAttribute(MODULE_KEY, module);
        model.addAttribute(ACTION_KEY, ADD_PATH);
        model.addAttribute(ACTION_TYPE_KEY, Boolean.TRUE);
    }

    /**
     * 修改页填充 提交地址，vo对象
     */
    protected void update(Model model, Object vo) {
        if (null == vo) {
            throw new PageException(PageEnum.NOT_FOUND);
        }
        model.addAttribute(VO_KEY, vo);
        model.addAttribute(MODULE_KEY, module);
        model.addAttribute(ACTION_KEY, UPDATE_PATH);
        model.addAttribute(ACTION_TYPE_KEY, Boolean.FALSE);
    }

    /**
     * 详情页填充：vo对象
     */
    protected void detail(Model model, Object vo) {
        if (null == vo) {
            throw new PageException(PageEnum.NOT_FOUND);
        }
        model.addAttribute(VO_KEY, vo);
        model.addAttribute(MODULE_KEY, module);
    }


    /**
     * 列表页填充：模块对象
     */
    protected void list(Model model) {
        model.addAttribute(MODULE_KEY, module);
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

    /**
     * 返回错误页面
     */
    protected String error() {
        return PageEnum.SERVER_ERROR.getPath();
    }

}
