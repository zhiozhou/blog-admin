package priv.zhou.module.system.dict.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.domain.po.DictDataPO;
import priv.zhou.module.system.dict.domain.po.DictPO;
import priv.zhou.module.system.dict.domain.query.DictQuery;
import priv.zhou.module.system.dict.domain.vo.DictTableVO;
import priv.zhou.module.system.dict.domain.vo.DictVO;

import java.util.List;


/**
 * 字典 数据访问模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Mapper
@Component
public interface DictDAO extends BaseDAO<DictPO, DictQuery> {

    int remove(Integer id);

    DictVO getVO(DictQuery query);

    List<DictTableVO> listTableVO(DictQuery query);
}
