package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dao;

import ${app.packet}.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}VO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}TableVO;

import java.util.List;


/**
 * ${table.comment} 数据访问模型
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Mapper
@Repository
public interface ${table.className}DAO extends BaseDAO<${table.className}PO, ${table.className}Query>{

#if(!$table.primaryKeys.isEmpty())

    ${table.className}VO removeList($table.primaryKeys[0].javaType[] ids);

    ${table.className}VO getVO(${table.className}Query query);

#end
    List<${table.className}TableVO> listTableVO(${table.className}Query query);

}
