package priv.zhou.module.system.extend.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.extend.domain.dao.TableDAO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;
import priv.zhou.module.system.extend.domain.po.TablePO;
import priv.zhou.module.system.extend.service.ITableService;

import java.util.List;

@Service
public class TableServiceImpl implements ITableService {

    private TableDAO tableDAO;

    public TableServiceImpl(TableDAO roleDAO) {
        this.tableDAO = roleDAO;
    }


    @Override
    public OutVO<ListVO<TableDTO>> list(TableDTO tableDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<TablePO> poList = tableDAO.list(tableDTO);
        PageInfo<TablePO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, TableDTO::new), pageInfo.getTotal());
    }


}
