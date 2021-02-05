package priv.zhou.module.system.image.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.Result;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.image.domain.dto.ImageDTO;

import java.util.List;

/**
 * 图片 服务层定义
 *
 * @author zhou
 * @since 2020.06.01
 */
public interface IImageService {

    Result<Integer> save(List<String> urlList, String remark);

    Result<NULL> remove(Integer id);

    default Result<List<ImageDTO>> list(ImageDTO imageDTO) {
        return list(imageDTO, null);
    }

    Result<List<ImageDTO>> list(ImageDTO imageDTO, Page page);
}
