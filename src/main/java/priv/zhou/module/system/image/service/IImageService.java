package priv.zhou.module.system.image.service;

import org.springframework.web.multipart.MultipartFile;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.module.system.image.domain.dto.ImageDTO;

/**
 * 图片 服务层定义
 *
 * @author zhou
 * @since 2020.06.01
 */
public interface IImageService {

    OutVO<NULL> upload(MultipartFile[] files);

    OutVO<ListVO<ImageDTO>> list(ImageDTO imageDTO, Page page);
}
