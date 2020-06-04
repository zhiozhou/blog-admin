package priv.zhou.module.system.image.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.system.image.domain.dto.ImageDTO;
import priv.zhou.module.system.image.domain.dto.ImagesDTO;
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


    @RequiresPermissions("system:image:save")
    @RequestMapping("/save")
    public OutVO<Integer> save(ImagesDTO imagesDTO) {
        return imageService.save(imagesDTO.getImageList(), imagesDTO.getRemark());
    }

    @RequiresPermissions("system:image:remove")
    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return imageService.remove(id);
    }


    @RequiresPermissions("system:image:list")
    @RequestMapping("/list")
    public OutVO<ListVO<ImageDTO>> list(ImageDTO imageDTO, Page page) {
        return imageService.list(imageDTO, page);
    }

}
