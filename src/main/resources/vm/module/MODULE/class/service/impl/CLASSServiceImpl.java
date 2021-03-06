package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.impl;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import ${app.packet}.common.constant.NULL;
import ${app.packet}.common.domain.Result;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.enums.ResultEnum;
import ${app.packet}.common.service.BaseService;
import ${app.packet}.framework.exception.RestException;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}VO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dao.${table.className}DAO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.I${table.className}Service;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}TableVO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;


/**
 * ${table.comment} 服务层实现
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Service
@RequiredArgsConstructor
public class ${table.className}ServiceImpl extends BaseService implements I${table.className}Service {

    private final ${table.className}DAO ${table.objectName}DAO;

    @Override
    public Result<NULL> save(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = new ${table.className}PO()#if($table.columnList.isEmpty());#end
#foreach($column in $table.columnList)
#if(!$table.primaryKeys.contains($column))

            .set${column.getSetName}(${table.objectName}DTO.get${column.getSetName}())#if($velocityCount == $table.columnList.size());
#end
#end
#end

        return ${table.objectName}DAO.save(${table.objectName}PO) > 0 ?
                Result.success():
                Result.fail(ResultEnum.LATER_RETRY);

    }

#if(!$table.primaryKeys.isEmpty())
    @Override
    @Transactional
    public Result<NULL> remove(List<${table.primaryKeys[0].javaType}> ${table.primaryKeys[0].attrName}s) {
        if(${table.objectName}DAO.removeList(${table.primaryKeys[0].attrName}s) != ${table.primaryKeys[0].attrName}s.length){
            throw new RestException(ResultEnum.LATER_RETRY);
        }
        return Result.success();
    }

    @Override
    public Result<NULL> update(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = new ${table.className}PO()#if($table.columnList.isEmpty());#end
#foreach($column in $table.columnList)
#if(!$table.primaryKeys.contains($column))

            .set${column.getSetName}(${table.objectName}DTO.get${column.getSetName}())#if($velocityCount == $table.columnList.size());
#end
#end
#end

        return  ${table.objectName}DAO.update(${table.objectName}PO) > 0 ?
                Result.success():
                Result.fail(ResultEnum.LATER_RETRY);

    }

    @Override
    public ${table.className}PO get(${table.className}Query query) {
        return ${table.objectName}DAO.get(query);
    }


    @Override
    public ${table.className}VO getVO(${table.className}Query query) {
        return ${table.objectName}DAO.getVO(query);
    }

#end
    @Override
    public List<${table.className}TableVO> listTableVO(${table.className}Query query, Page page) {
        startPage(page);
        return ${table.objectName}DAO.listTableVO(query);
    }
}
