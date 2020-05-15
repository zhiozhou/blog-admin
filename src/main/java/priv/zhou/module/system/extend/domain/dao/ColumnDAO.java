package priv.zhou.module.system.extend.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.module.system.extend.domain.dto.ColumnDTO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;
import priv.zhou.module.system.extend.domain.po.ColumnPO;
import priv.zhou.module.system.extend.domain.po.TablePO;

import java.util.List;

@Mapper
@Component
public interface ColumnDAO {

    List<ColumnPO> list(ColumnDTO columnDTO);

}
