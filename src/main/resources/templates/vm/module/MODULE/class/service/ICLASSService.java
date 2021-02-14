package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service;

import java.util.List;
import ${app.packet}.common.constant.NULL;
import ${app.packet}.common.domain.Result;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}VO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}TableVO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;

/**
 * ${table.comment} 服务层定义
 *
 * @author ${app.author}
 * @since ${app.since}
 */
public interface I${table.className}Service {

    Result<NULL> save(${table.className}DTO ${table.objectName}DTO);

#if(!$table.primaryKeys.isEmpty())
    Result<NULL> remove(${table.primaryKeys[0].javaType}[] ids);

    Result<NULL> update(${table.className}DTO ${table.objectName}DTO);

    ${table.className}PO get(${table.className}Query query);

    ${table.className}VO getVO(${table.className}Query query);
#end

    List<${table.className}TableVO> listTableVO(${table.className}Query query, Page page);

}
