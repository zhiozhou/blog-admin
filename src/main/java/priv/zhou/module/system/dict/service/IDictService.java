package priv.zhou.module.system.dict.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.constant.NULL;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.po.DictDataPO;
import priv.zhou.module.system.dict.domain.query.DictDataQuery;
import priv.zhou.module.system.dict.domain.query.DictQuery;
import priv.zhou.module.system.dict.domain.vo.DictDataVO;
import priv.zhou.module.system.dict.domain.vo.DictTableVO;
import priv.zhou.module.system.dict.domain.vo.DictVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static priv.zhou.common.constant.GlobalConst.EMPTY_STR;

/**
 * 字典 服务层定义
 *
 * @author zhou
 * @since 2020.04.17
 */
public interface IDictService {

    Result<NULL> save(DictDTO dictDTO);

    Result<NULL> remove(List<Integer> idList);

    Result<NULL> update(DictDTO dictDTO);

    DictVO getVO(DictQuery query);

    default List<DictTableVO> listTableVO(DictQuery query) {
        return listTableVO(query, null);
    }

    List<DictTableVO> listTableVO(DictQuery query, Page page);


    DictDataPO getData(DictDataQuery query);

    default String getLabel(String key, Integer code) {
        return getLabel(key, String.valueOf(code));
    }

    default String getLabel(String key, String code) {
        DictDataPO dataPO = getData(new DictDataQuery().setDictKey(key).setCode(code));
        return null == dataPO ? EMPTY_STR : dataPO.getLabel();
    }

    List<DictDataPO> listData(DictDataQuery query);

    default List<DictDataVO> listDataVO(String dictKey) {
        return listDataVO(dictKey, null);
    }

    default List<DictDataVO> listDataVO(String dictKey, Integer type) {
        return listDataVO(new DictDataQuery().setDictKey(dictKey).setType(type));
    }

    List<DictDataVO> listDataVO(DictDataQuery query);

    default Map<String, DictDataVO> mapDataVO(String dictKey) {
        return mapDataVO(dictKey, null);
    }

    default Map<String, DictDataVO> mapDataVO(String dictKey, Integer type) {
        return mapDataVO(new DictDataQuery().setDictKey(dictKey).setType(type));
    }

    default Map<String, DictDataVO> mapDataVO(DictDataQuery query) {
        return listDataVO(query).stream().collect(Collectors.toMap(DictDataVO::getCode, vo -> vo));
    }
}
