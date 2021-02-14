package priv.zhou.module.system.extend.domain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.module.system.extend.domain.bo.TableBO;
import priv.zhou.module.system.extend.domain.query.TableQuery;
import priv.zhou.module.system.extend.domain.vo.TableTableVO;

import java.util.List;

@Mapper
@Component
public interface TableDAO {

    List<TableTableVO> listTableVO(TableQuery query);

    List<TableBO> listBO(TableQuery query);

}
