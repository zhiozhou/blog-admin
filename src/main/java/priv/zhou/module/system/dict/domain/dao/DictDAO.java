package priv.zhou.module.system.dict.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.common.domain.dao.BaseDAO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.domain.po.DictDataPO;
import priv.zhou.module.system.dict.domain.po.DictPO;

import java.util.List;


/**
 * 字典 数据访问模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Mapper
@Component
public interface DictDAO extends BaseDAO<DictDTO, DictPO> {

    int saveData(DictPO dictPO);

    int removeData(DictDTO dictDTO);

    List<DictDataPO> listData(DictDataDTO dictDataDTO);

    DictDataPO getData(DictDTO dictDTO);

}
