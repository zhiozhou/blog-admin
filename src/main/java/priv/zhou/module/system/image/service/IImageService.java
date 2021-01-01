package priv.zhou.module.system.image.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.TableVO;
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

    OutVO<Integer> save(List<String> urlList, String remark);

    OutVO<NULL> remove(Integer id);

    default OutVO<TableVO<ImageDTO>> list(ImageDTO imageDTO) {
        return list(imageDTO, null);
    }

    OutVO<TableVO<ImageDTO>> list(ImageDTO imageDTO, Page page);
}
