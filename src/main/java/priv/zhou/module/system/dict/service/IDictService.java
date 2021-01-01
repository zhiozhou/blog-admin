package priv.zhou.module.system.dict.service;

import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典 服务层定义
 *
 * @author zhou
 * @since 2020.04.17
 */
public interface IDictService {

    Integer PUBLIC_TYPE = 0;

    String DICT_SNS_KEY = "zhou_sns";

    Result<NULL> save(DictDTO dictDTO);

    Result<NULL> remove(DictDTO dictDTO);

    Result<NULL> update(DictDTO dictDTO);

    Result<DictDTO> get(DictDTO dictDTO);

    default Result<List<DictDTO>> list(DictDTO dictDTO) {
        return list(dictDTO, null);
    }

    Result<List<DictDTO>> list(DictDTO dictDTO, Page page);

    Result<DictDataDTO> getData(DictDataDTO dictDataDTO);

    default String getLabel(String key, Integer code) {
        return getLabel(key, String.valueOf(code));
    }

    default String getLabel(String key, String code) {
        Result<DictDataDTO> getRes = getData(new DictDataDTO().setDictKey(key).setCode(code));
        return getRes.isFail() ? "" : getRes.getData().getLabel();
    }

    List<DictDataDTO> listData(String dictKey, boolean all);

    default List<DictDataDTO> listData(String dictKey) {
        return listData(dictKey, false);
    }

    default Map<String, DictDataDTO> mapData(String dictKey, boolean all) {
        return listData(dictKey, all).stream().collect(Collectors.toMap(DictDataDTO::getCode, dto -> dto));
    }

    default Map<String, DictDataDTO> mapData(String dictKey) {
        return mapData(dictKey, false);
    }
}
