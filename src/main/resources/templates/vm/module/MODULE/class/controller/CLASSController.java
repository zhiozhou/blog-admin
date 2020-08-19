package ${app.packet}.module.${app.module}.${table.objectName}.controller;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ${app.packet}.common.domain.Module;
import ${app.packet}.common.domain.vo.OutVO;
import ${app.packet}.common.controller.BaseController;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.${app.module}.${table.objectName}.service.I${table.className}Service;

/**
 * ${table.comment} 视图控制层
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Component
@RequestMapping("/${app.module}/${table.objectName}")
public class ${table.className}Controller extends BaseController {

    private final I${table.className}Service ${table.objectName}Service;

    public ${table.className}Controller(I${table.className}Service ${table.objectName}Service) {
        super(new Module("${table.comment}","${app.module}:${table.objectName}"));
        this.${table.objectName}Service = ${table.objectName}Service;
    }

    @RequestMapping("/add")
    public String add(Model model) {
        super.add(model, new ${table.className}DTO());

        return "${app.module}/${table.objectName}/au";
    }

    @RequestMapping("/update/{id}")
    public String update(Model model, @PathVariable Integer id) {

        OutVO<${table.className}DTO> dtoVO = ${table.objectName}Service.get(new ${table.className}DTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.update(model, dtoVO.getData());

        return "${app.module}/${table.objectName}/au";
    }

    @RequestMapping("/detail/{id}")
    public String detail(Model model, @PathVariable Integer id) {
        OutVO<${table.className}DTO> dtoVO = ${table.objectName}Service.get(new ${table.className}DTO().setId(id));
        if (dtoVO.isFail()) {
            return NOT_FOUNT;
        }
        super.detail(model, dtoVO.getData());

        return "${app.module}/${table.objectName}/detail";
    }

    @RequestMapping("/list")
    public String list(Model model) {
        super.list(model);

        return "${app.module}/${table.objectName}/list";
    }
}
