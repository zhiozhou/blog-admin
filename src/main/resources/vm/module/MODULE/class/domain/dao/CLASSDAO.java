package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dao;

import org.springframework.stereotype.Repository;
import ${app.packet}.common.domain.dao.BaseDAO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}VO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}TableVO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;



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

    int removeList(List<${table.primaryKeys[0].javaType}> ${table.primaryKeys[0].attrName}s);

    ${table.className}VO getVO(${table.className}Query query);

#end
    List<${table.className}TableVO> listTableVO(${table.className}Query query);

}
