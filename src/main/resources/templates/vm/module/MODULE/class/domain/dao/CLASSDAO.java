package ${app.packet}.module.${app.module}.${table.objectName}.domain.dao;

import ${app.packet}.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.po.${table.className}PO;

import java.util.List;


/**
 * ${table.comment} 数据访问模型
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Mapper
@Component
public interface ${table.className}DAO extends BaseDAO<${table.className}DTO,${table.className}PO>{
}
