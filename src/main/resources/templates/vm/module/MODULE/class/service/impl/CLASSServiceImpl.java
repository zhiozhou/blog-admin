package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.impl;

import lombok.RequiredArgsConstructor;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.vo.Result;
import ${app.packet}.common.param.NULL;
import ${app.packet}.common.service.BaseService;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.common.param.ResultEnum;
import ${app.packet}.common.domain.dto.DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dao.${table.className}DAO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}TableVO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}VO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.I${table.className}Service;
import static java.util.Objects.isNull;
import java.util.List;


/**
 * ${table.comment} 服务层实现
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Service
@RequiredArgsConstructor
public class ${table.className}ServiceImpl extends BaseServic implements I${table.className}Service {

    private final ${table.className}DAO ${table.objectName}DAO;

    @Override
    public Result<NULL> save(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = new ${table.className}PO()#if($velocityCount != $table.columnList.size());
#end
#foreach($column in $table.columnList)
#if($column.name != $table.primaryKey.name)
            .set${column.getSetName}(${table.objectName}DTO.get${column.getSetName}())#if($velocityCount != $table.columnList.size());
#end
#end
#end
        return ${table.objectName}DAO.save(${table.objectName}PO) > 0 ?
                Result.success():
                Result.fail(ResultEnum.LATER_RETRY);

    }

#if(!$table.primaryKeys.isEmpty())
    @Override
    public Result<NULL> remove(${table.primaryKeys[0].javaType}[] ids) {
        return  ${table.objectName}DAO.removeList(ids) > 0 ?
            Result.success():
            Result.fail(ResultEnum.FAIL_OPERATION);
    }

    @Override
    public Result<NULL> update(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DTO.toPO();
        return  ${table.objectName}DAO.update(${table.objectName}PO) > 0 ?
                Result.success():
                Result.fail(ResultEnum.FAIL_OPERATION);

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
