package priv.zhou.module.system.image.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import priv.zhou.common.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * 图片 视图控制层
 *
 * @author zhou
 * @since 2020.06.01
 */
@Controller
@RequestMapping("/system/image")
public class ImageController extends BaseController {

    public ImageController() {
        super("图片", "system:image");
    }

    @RequiresPermissions("system:image:list")
    @RequestMapping("/list")
    public String list(Model model, HttpServletRequest request) {
        super.upload(model);
        super.list(model);
//        RedisUtil.addSet(FILE_SERVICE_IP_SET_KEY, HttpUtil.getIpAddress(request));
        return "system/image/list";
    }
}
