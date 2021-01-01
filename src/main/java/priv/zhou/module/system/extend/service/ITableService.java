package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;

public interface ITableService {

    default OutVO<TableVO<TableDTO>> list(TableDTO tableDTO) {
        return list(tableDTO, null);
    }

    OutVO<TableVO<TableDTO>> list(TableDTO tableDTO, Page page);
}
