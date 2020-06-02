package priv.zhou.module.system.image.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.module.system.image.service.IImageService;

/**
 * 图片 视图控制层
 *
 * @author zhou
 * @since 2020.06.01
 */
@Component
@RequestMapping("/system/image")
public class ImageController extends BaseController {

    private final Module module = new Module("图片", "system:image");

    @RequiresPermissions("system:image:list")
    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);
        return "system/image/list";
    }
}
