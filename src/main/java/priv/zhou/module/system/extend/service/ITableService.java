package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;

public interface ITableService {

    OutVO<ListVO<TableDTO>> list(TableDTO tableDTO, Page page);
}
