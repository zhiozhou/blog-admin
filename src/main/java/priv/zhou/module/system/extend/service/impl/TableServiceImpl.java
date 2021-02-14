package priv.zhou.module.system.extend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.service.BaseService;
import priv.zhou.module.system.extend.domain.dao.TableDAO;
import priv.zhou.module.system.extend.domain.query.TableQuery;
import priv.zhou.module.system.extend.domain.vo.TableTableVO;
import priv.zhou.module.system.extend.service.ITableService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl extends BaseService implements ITableService {

    private final TableDAO tableDAO;


    @Override
    public List<TableTableVO> listTableVO(TableQuery query, Page page) {
        startPage(page);
        return tableDAO.listTableVO(query);
    }


}
