package priv.zhou.module.system.image.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO1;
import priv.zhou.module.system.image.domain.dto.ImageDTO;
import priv.zhou.module.system.image.domain.po.ImagePO;


/**
 * 图片 数据访问模型
 *
 * @author zhou
 * @since 2020.06.01
 */
@Mapper
@Component
public interface ImageDAO extends BaseDAO1<ImageDTO, ImagePO>{
}
