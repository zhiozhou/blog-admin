package priv.zhou.module.system.image.controller;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.module.system.image.domain.dto.ImageDTO;
import priv.zhou.module.system.image.service.IImageService;

import java.util.List;

/**
 * 图片 控制层
 *
 * @author zhou
 * @since 2020.06.01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/image/rest")
public class ImageRestController {

    private final IImageService imageService;

    @RequiresPermissions("system:image:save")
    @PostMapping("/save")
    public Result<Integer> save(@RequestParam(value = "urlList[]") List<String> urlList, String remark) {
        return imageService.save(urlList, remark);
    }

    @RequiresPermissions("system:image:remove")
    @PostMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return imageService.remove(id);
    }


    @RequiresPermissions("system:image:view")
    @PostMapping("/list")
    public Result<TableVO<ImageDTO>> list(ImageDTO imageDTO, Page page) {
        return Result.table(imageService.list(imageDTO, page));
    }

}
