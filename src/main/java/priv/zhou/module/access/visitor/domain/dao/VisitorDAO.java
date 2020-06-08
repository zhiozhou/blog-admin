package priv.zhou.module.access.visitor.domain.dao;

import priv.zhou.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;
import priv.zhou.module.access.visitor.domain.po.VisitorPO;

import java.util.List;


/**
 * 访客 数据访问模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Mapper
@Component
public interface VisitorDAO extends BaseDAO<VisitorDTO,VisitorPO>{
}
