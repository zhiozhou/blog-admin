package ${app.packet}.module.$!{app.moduleRef}${table.className}.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.Result;
import ${app.packet}.common.domain.vo.TableVO;
import ${app.packet}.common.constant.NULL;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo.${table.className}TableVO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.I${table.className}Service;

import javax.validation.Valid;

/**
 * ${table.comment} 控制层
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@RestController
@RequestMapping("/$!{app.modulePath}${table.objectName}/rest")
public class ${table.className}RestController {

    private final I${table.className}Service ${table.objectName}Service;

    public ${table.className}RestController(I${table.className}Service ${table.objectName}Service) {
        this.${table.objectName}Service = ${table.objectName}Service;
    }

    @PostMapping("/save")
    public Result<NULL> save(@Valid ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.save(${table.objectName}DTO);
    }

    @PostMapping("/remove")
    public Result<NULL> remove(${table.primaryKeys[0].javaType}[] ids) {
        return ${table.objectName}Service.remove(ids);
    }

    @PostMapping("/update/{id}")
    public Result<NULL> update(@PathVariable Integer id, @Valid ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.update(${table.objectName}DTO.setId(id));
    }

    @PostMapping("/list")
    public Result<TableVO<${table.className}TableVO>> list(${table.className}Query query, Page page) {
        return Result.table(${table.objectName}Service.listTableVO(query, page));
    }

}
