package priv.zhou.module.system.image.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.system.image.domain.dto.ImageDTO;
import priv.zhou.module.system.image.service.IImageService;

/**
 * 图片 控制层
 *
 * @author zhou
 * @since 2020.06.01
 */
@RestController
@RequestMapping("/system/image/rest")
public class ImageRestController {

    private final IImageService imageService;

    public ImageRestController(IImageService imageService) {
        this.imageService = imageService;
    }

    @RequiresPermissions("system:image:upload")
    @RequestMapping("/upload")
    public OutVO<NULL> save(@RequestParam("file") MultipartFile[] files) {
        return imageService.upload(files);
    }


    @RequiresPermissions("system:image:list")
    @RequestMapping("/list")
    public OutVO<ListVO<ImageDTO>> list(ImageDTO imageDTO, Page page) {
        return imageService.list(imageDTO, page);
    }

}
