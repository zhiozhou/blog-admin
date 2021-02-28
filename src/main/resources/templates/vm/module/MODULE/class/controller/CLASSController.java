package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import ${app.packet}.common.domain.Module;
import ${app.packet}.common.controller.BaseController;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query.${table.className}Query;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.I${table.className}Service;

/**
 * ${table.comment} 视图控制层
 *
 * @author ${app.author}
 * @since ${app.since}
 */

@Controller
@RequestMapping("/$!{app.modulePath}${table.objectName}")
public class ${table.className}Controller extends BaseController {

    private final I${table.className}Service ${table.objectName}Service;

    #if($app.module != "")
        #set($modulePrefix=$app.module+":")
    #end

    public ${table.className}Controller(I${table.className}Service ${table.objectName}Service) {
        super("${table.comment}","$!{modulePrefix}${table.objectName}");
        this.${table.objectName}Service = ${table.objectName}Service;
    }

    @RequiresPermissions("$!{modulePrefix}${table.objectName}:save")
    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new ${table.className}DTO());
        return "$!{app.modulePath}${table.objectName}/au";
    }

#if(!$table.primaryKeys.isEmpty())
    @RequiresPermissions("$!{modulePrefix}${table.objectName}:update")
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        super.update(model, ${table.objectName}Service.getVO(new ${table.className}Query().setId(id)));
        return "$!{app.modulePath}${table.objectName}/au";
    }

    @RequiresPermissions("$!{modulePrefix}${table.objectName}:detail")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        super.detail(model, ${table.objectName}Service.getVO(new ${table.className}Query().setId(id)));
        return "$!{app.modulePath}${table.objectName}/detail";
    }

#end
    @RequiresPermissions("$!{modulePrefix}${table.objectName}:view")
    @GetMapping
    public String view(Model model) {
        super.list(model);
        return "$!{app.modulePath}${table.objectName}/index";
    }
}
