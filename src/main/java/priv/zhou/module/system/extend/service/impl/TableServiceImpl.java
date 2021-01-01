package priv.zhou.module.system.extend.service.impl;

import org.springframework.stereotype.Service;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.service.BaseService;
import priv.zhou.module.system.extend.domain.dao.TableDAO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;
import priv.zhou.module.system.extend.service.ITableService;

import java.util.List;

@Service
public class TableServiceImpl extends BaseService implements ITableService {

    private TableDAO tableDAO;

    public TableServiceImpl(TableDAO roleDAO) {
        this.tableDAO = roleDAO;
    }


    @Override
    public Result<List<TableDTO>> list(TableDTO tableDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(tableDAO.list(tableDTO), TableDTO::new));
    }


}
