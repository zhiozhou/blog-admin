package priv.zhou.module.access.accessLog.domain.dao;

import priv.zhou.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import priv.zhou.module.access.accessLog.domain.dto.AccessLogDTO;
import priv.zhou.module.access.accessLog.domain.po.AccessLogPO;

import java.util.List;


/**
 * 访问日志 数据访问模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Mapper
@Component
public interface AccessLogDAO extends BaseDAO<AccessLogDTO,AccessLogPO>{
}
