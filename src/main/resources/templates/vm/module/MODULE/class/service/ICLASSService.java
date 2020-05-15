package ${app.packet}.module.${app.module}.${table.objectName}.service;

import com.alibaba.fastjson.JSONObject;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.param.NULL;
import ${app.packet}.common.domain.vo.OutVO;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.dto.${table.className}DTO;

/**
 * ${table.comment} 服务层定义
 *
 * @author ${app.author}
 * @since ${app.since}
 */
public interface I${table.className}Service {

    OutVO<NULL> save(${table.className}DTO ${table.objectName}DTO);

    OutVO<NULL> remove(${table.className}DTO ${table.objectName}DTO);

    OutVO<NULL> update(${table.className}DTO ${table.objectName}DTO);

    OutVO<${table.className}DTO> get(${table.className}DTO ${table.objectName}DTO);

    OutVO<ListVO<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO, Page page);

    OutVO<Integer> count(${table.className}DTO ${table.objectName}DTO);
}
