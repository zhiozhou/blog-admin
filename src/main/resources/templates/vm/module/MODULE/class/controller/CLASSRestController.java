package ${app.packet}.module.${app.module}.${table.className}.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.vo.OutVO;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.common.param.NULL;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.${app.module}.${table.objectName}.service.I${table.className}Service;

import javax.validation.Valid;

/**
 * ${table.comment} 控制层
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@RestController
@RequestMapping("/${app.module}/${table.objectName}/rest")
public class ${table.className}RestController {

    private final I${table.className}Service ${table.objectName}Service;

    public ${table.className}RestController(I${table.className}Service ${table.objectName}Service) {
        this.${table.objectName}Service = ${table.objectName}Service;
    }

    @RequestMapping("/save")
    public OutVO<NULL> save(@Valid ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.save(${table.objectName}DTO);
    }

    @RequestMapping("/remove/{id}")
    public OutVO<NULL> remove(@PathVariable Integer id) {
        return ${table.objectName}Service.remove(new ${table.className}DTO().setId(id));
    }

    @RequestMapping("/update")
    public OutVO<NULL> update(@Valid ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.update(${table.objectName}DTO);
    }


    @RequestMapping("/list")
    public OutVO<ListVO<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO, Page page) {
        return ${table.objectName}Service.list(${table.objectName}DTO, page);
    }

}
