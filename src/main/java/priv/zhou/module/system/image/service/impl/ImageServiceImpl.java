package priv.zhou.module.system.image.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.module.system.image.domain.dao.ImageDAO;
import priv.zhou.module.system.image.domain.dto.ImageDTO;
import priv.zhou.module.system.image.domain.po.ImagePO;
import priv.zhou.module.system.image.service.IImageService;

import java.util.List;


/**
 * 图片 服务层实现
 *
 * @author zhou
 * @since 2020.06.01
 */
@Service
public class ImageServiceImpl implements IImageService {

    private final ImageDAO imageDAO;

    public ImageServiceImpl(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    @Override
    public OutVO<NULL> upload(MultipartFile[] files) {
        if (ArrayUtils.isEmpty(files)) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        return null;
    }

    @Override
    public OutVO<ListVO<ImageDTO>> list(ImageDTO imageDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<ImagePO> poList = imageDAO.list(imageDTO);
        PageInfo<ImagePO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, ImageDTO::new), pageInfo.getTotal());
    }
}
