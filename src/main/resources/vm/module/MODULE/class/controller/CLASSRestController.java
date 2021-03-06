package ${app.packet}.module.$!{app.moduleRef}${table.className}.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${app.packet}.common.domain.Result;
import ${app.packet}.common.constant.NULL;
import ${app.packet}.common.constant.Update;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.vo.TableVO;
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
@RequiredArgsConstructor
@RequestMapping("/$!{app.modulePath}${table.objectName}/rest")
public class ${table.className}RestController {

    private final I${table.className}Service ${table.objectName}Service;

    @RequiresPermissions("$!{modulePrefix}${table.objectName}:save")
    @PostMapping("/save")
    public Result<NULL> save(@Valid ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.save(${table.objectName}DTO);
    }

    @RequiresPermissions("$!{modulePrefix}${table.objectName}:remove")
    @PostMapping("/remove")
    public Result<NULL> remove(List<${table.primaryKeys[0].javaType}> ${table.primaryKeys[0].attrName}s) {
        return ${table.objectName}Service.remove(${table.primaryKeys[0].attrName}s);
    }

    @RequiresPermissions("$!{modulePrefix}${table.objectName}:update")
    @PostMapping("/update")
    public Result<NULL> update(@Validated({Update.class}) ${table.className}DTO ${table.objectName}DTO) {
        return ${table.objectName}Service.update(${table.objectName}DTO);
    }

    @RequiresPermissions("$!{modulePrefix}${table.objectName}:view")
    @PostMapping("/list")
    public Result<TableVO<${table.className}TableVO>> list(${table.className}Query query, Page page) {
        return Result.table(${table.objectName}Service.listTableVO(query, page));
    }

}
