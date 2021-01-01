package priv.zhou.module.system.extend.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.module.system.extend.domain.dto.TableDTO;
import priv.zhou.module.system.extend.service.IExtendService;
import priv.zhou.module.system.extend.service.ITableService;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static priv.zhou.common.misc.AppProperties.ENC;

/**
 * 模块生成 接口控制层
 *
 * @author zhou
 * @since 2020.04.16
 */
@RestController
@RequestMapping("/system/extend/rest")
public class ExtendRestController {

    private final ITableService tableService;

    private final IExtendService extendService;

    public ExtendRestController(ITableService tableService, IExtendService extendService) {
        this.tableService = tableService;
        this.extendService = extendService;
    }

    @RequiresPermissions("system:extend:index")
    @RequestMapping("/table/list")
    public OutVO<TableVO<TableDTO>> list(TableDTO tableDTO, Page page) {
        return tableService.list(tableDTO, page);
    }


    @RequiresPermissions("system:extend:extend")
    @GetMapping("/extend")
    public OutVO<byte[]> extend(HttpServletResponse response, String tableNames) throws Exception {


        OutVO<byte[]> outVo = extendService.module(Arrays.asList(tableNames.split(",")));
        if (outVo.isFail()) {
            return outVo;
        }

        byte[] data = outVo.getData();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"extend-" + tableNames + ".zip\"");
        response.addHeader("Content-Length", String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=" + ENC);
        response.getOutputStream().write(data);

        return null;
    }


}
