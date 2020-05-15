package priv.zhou.module.system.dict.service;

import com.alibaba.fastjson.JSONObject;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;

import java.util.List;
import java.util.Map;

/**
 * 字典 服务层定义
 *
 * @author zhou
 * @since 2020.04.17
 */
public interface IDictService {

    OutVO<NULL> save(DictDTO dictDTO);

    OutVO<NULL> remove(DictDTO dictDTO);

    OutVO<NULL> update(DictDTO dictDTO);

    OutVO<DictDTO> get(DictDTO dictDTO);

    OutVO<DictDataDTO> getData(DictDTO dictDTO);

    OutVO<ListVO<DictDTO>> list(DictDTO dictDTO, Page page);

    OutVO<Map<String, DictDataDTO>> dataMap(DictDTO dictDTO, boolean noSystem);

    default OutVO<Map<String, DictDataDTO>> dataMap(DictDTO dictDTO) {
        return dataMap(dictDTO, true);
    }

    OutVO<List<DictDataDTO>> dataList(DictDTO dictDTO, boolean noSystem);

    default OutVO<List<DictDataDTO>> dataList(DictDTO dictDTO) {
        return dataList(dictDTO, true);
    }
    OutVO<Integer> count(DictDTO dictDTO);
}
