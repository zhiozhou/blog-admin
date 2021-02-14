package ${app.packet}.module.$!{app.moduleRef}${table.className}.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.vo.OutVO;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.common.param.NULL;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
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

    @PostMapping("/remove/{id}")
    public Result<NULL> remove(@PathVariable Integer id) {
        return ${table.objectName}Service.remove(new ${table.className}DTO().setId(id));
    }

    @PostMapping("/update/{id}")
    public Result<NULL> update(@PathVariable Integer id,@Valid ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.update(${table.objectName}DTO.setId(id));
    }

    @PostMapping("/list")
    public Result<ListVO<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO, Page page) {
        return Result.success(${table.objectName}Service.list(${table.objectName}DTO, page));
    }

}
