package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service;

import com.alibaba.fastjson.JSONObject;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.param.NULL;
import ${app.packet}.common.domain.vo.Result;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;

/**
 * ${table.comment} 服务层定义
 *
 * @author ${app.author}
 * @since ${app.since}
 */
public interface I${table.className}Service {

    Result<NULL> save(${table.className}DTO ${table.objectName}DTO);

    Result<NULL> remove(${table.className}DTO ${table.objectName}DTO);

    Result<NULL> update(${table.className}DTO ${table.objectName}DTO);

    Result<${table.className}DTO> get(${table.className}DTO ${table.objectName}DTO);

    default Result<List<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO){
        return list(${table.objectName}DTO, null);
    }
    
    Result<List<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO, Page page);
}
