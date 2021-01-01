package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;

import java.util.List;

public interface ITableService {

    default Result<List<TableDTO>> list(TableDTO tableDTO) {
        return list(tableDTO, null);
    }

    Result<List<TableDTO>> list(TableDTO tableDTO, Page page);
}
