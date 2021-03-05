package priv.zhou.module.system.image.service.impl;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.properties.AppProperties;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
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
@RequiredArgsConstructor
public class ImageServiceImpl extends BaseService implements IImageService {

    private final ImageDAO imageDAO;

    private final AppProperties appProperties;

    @Override
    public Result<Integer> save(List<String> urlList, String remark) {
        if (null == urlList) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }
        int failCount = 0;
        Integer userId = ShiroUtil.getUserId();
        for (String url : urlList) {
            if (imageDAO.save(new ImagePO().setUrl(url).setRemark(remark).setCreateId(userId)) < 1) {
                failCount++;
            }
        }
        return Result.success(failCount);
    }

    @Override
    @Transactional
    public Result<NULL> remove(Integer id) {
        if (null == id) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }
        ImageDTO queryDTO = new ImageDTO().setId(id);
        ImagePO imagePO = imageDAO.get(queryDTO);
        if (null != imagePO) {
            if (imageDAO.remove(queryDTO) < 1) {
                return Result.fail(ResultEnum.LATER_RETRY);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("url", imagePO.getUrl());
            Result<NULL> removeRes = httpPost("移除图片", appProperties.getFileService() + "/remove", params);
            if (removeRes.isFail()) {
                throw new GlobalException(ResultEnum.LATER_RETRY);
            }
        }
        return Result.success();
    }

    @Override
    public Result<List<ImageDTO>> list(ImageDTO imageDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(imageDAO.list(imageDTO), ImageDTO::new));
    }
}
