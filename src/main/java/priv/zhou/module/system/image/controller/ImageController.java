package priv.zhou.module.system.image.controller;

import com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import priv.zhou.common.controller.BaseController;
import priv.zhou.common.domain.Module;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.service.IDictService;
import priv.zhou.module.system.image.service.IImageService;

import javax.validation.Valid;

/**
 * 图片 视图控制层
 *
 * @author zhou
 * @since 2020.06.01
 */
@Component
@RequestMapping("/system/image")
public class ImageController extends BaseController {

    private final IImageService imageService;

    private final Module module = new Module("图片", "system:image");

    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @RequiresPermissions("system:image:list")
    @RequestMapping("/list")
    public String list(Model model) {
        fillList(model, module);
        return "system/image/list";
    }
}
