package priv.zhou.module.system.extend.service;

import priv.zhou.common.domain.dto.Page;
import priv.zhou.module.system.extend.domain.query.TableQuery;
import priv.zhou.module.system.extend.domain.vo.TableTableVO;

import java.util.List;

public interface ITableService {

    List<TableTableVO> listTableVO(TableQuery query, Page page);

}
