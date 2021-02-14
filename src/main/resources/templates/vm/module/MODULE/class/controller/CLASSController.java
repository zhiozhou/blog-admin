package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import ${app.packet}.common.domain.Module;
import ${app.packet}.common.domain.vo.Result;
import priv.zhou.common.domain.vo.TableVO;
import ${app.packet}.common.controller.BaseController;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.I${table.className}Service;

/**
 * ${table.comment} 视图控制层
 *
 * @author ${app.author}
 * @since ${app.since}
 */

@Component
@RequestMapping("/$!{app.modulePath}${table.objectName}")
public class ${table.className}Controller extends BaseController {

    private final I${table.className}Service ${table.objectName}Service;

    #if($app.module != "")
        #set($modulePrefix=$app.module+":")
    #end

    public ${table.className}Controller(I${table.className}Service ${table.objectName}Service) {
        super(new Module("${table.comment}","$!{modulePrefix}${table.objectName}"));
        this.${table.objectName}Service = ${table.objectName}Service;
    }

    @GetMapping("/add")
    public String add(Model model) {
        super.add(model, new ${table.className}DTO());

        return "$!{app.modulePath}${table.objectName}/au";
    }

#if(!$table.primaryKeys.isEmpty())
    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {
        super.update(model, ${table.objectName}Service.getV0(new ${table.className}Query().setId(id)));
        return "$!{app.modulePath}${table.objectName}/au";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        super.detail(model, ${table.objectName}Service.getV0(new ${table.className}Query().setId(id)));
        return "$!{app.modulePath}${table.objectName}/detail";
    }

#end
    @GetMapping("/list")
    public String list(Model model) {
        super.list(model);

        return "$!{app.modulePath}${table.objectName}/list";
    }
}
