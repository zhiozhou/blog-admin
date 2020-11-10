package priv.zhou.module.system.image.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.AppProperties;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.image.domain.dao.ImageDAO;
import priv.zhou.module.system.image.domain.dto.ImageDTO;
import priv.zhou.module.system.image.domain.po.ImagePO;
import priv.zhou.module.system.image.service.IImageService;

import java.util.List;
import java.util.Map;

import static priv.zhou.common.tools.OkHttpUtil.httpPost;


/**
 * 图片 服务层实现
 *
 * @author zhou
 * @since 2020.06.01
 */
@Service
public class ImageServiceImpl implements IImageService {

    private final ImageDAO imageDAO;

    private final AppProperties appProperties;

    public ImageServiceImpl(ImageDAO imageDAO, AppProperties appProperties) {
        this.imageDAO = imageDAO;
        this.appProperties = appProperties;
    }

    @Override
    public OutVO<Integer> save(List<String> urlList, String remark) {
        if (null == urlList) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        int failCount = 0;
        Integer userId = ShiroUtil.getUserId();
        for (String url : urlList) {
            if (imageDAO.save(new ImagePO().setUrl(url).setRemark(remark).setCreateId(userId)) < 1) {
                failCount++;
            }
        }
        return OutVO.success(failCount);
    }

    @Override
    @Transactional
    public OutVO<NULL> remove(Integer id) {
        if (null == id) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        ImageDTO queryDTO = new ImageDTO().setId(id);
        ImagePO imagePO = imageDAO.get(queryDTO);
        if (null != imagePO) {
            if (imageDAO.remove(queryDTO) < 1) {
                return OutVO.fail(OutVOEnum.FAIL_OPERATION);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("url", imagePO.getUrl());
            OutVO<NULL> removeRes = httpPost("移除图片", appProperties.getFileService() + "/remove", params);
            if (removeRes.isFail()) {
                throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
            }
        }
        return OutVO.success();
    }

    @Override
    public OutVO<ListVO<ImageDTO>> list(ImageDTO imageDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<ImagePO> poList = imageDAO.list(imageDTO);
        PageInfo<ImagePO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, ImageDTO::new), pageInfo.getTotal());
    }
}
