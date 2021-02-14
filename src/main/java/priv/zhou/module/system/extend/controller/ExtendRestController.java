package priv.zhou.module.system.extend.controller;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.system.extend.domain.query.TableQuery;
import priv.zhou.module.system.extend.domain.vo.TableTableVO;
import priv.zhou.module.system.extend.service.IExtendService;
import priv.zhou.module.system.extend.service.ITableService;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static priv.zhou.common.constant.GlobalConst.DEFAULT_CHARSET;

/**
 * 模块生成 接口控制层
 *
 * @author zhou
 * @since 2020.04.16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/extend/rest")
public class ExtendRestController {

    private final ITableService tableService;

    private final IExtendService extendService;


    @RequiresPermissions("system:extend:index")
    @RequestMapping("/table/list")
    public Result<TableVO<TableTableVO>> list(TableQuery query, Page page) {
        return Result.table(tableService.listTableVO(query, page));
    }

    @RequiresPermissions("system:extend:extend")
    @GetMapping("/extend")
    public Result<byte[]> extend(HttpServletResponse response, String tableNames) throws Exception {


        Result<byte[]> result = extendService.module(Arrays.asList(tableNames.split(",")));
        if (result.isFail()) {
            return result;
        }

        byte[] data = result.getData();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"extend-" + tableNames + ".zip\"");
        response.addHeader("Content-Length", String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=" + DEFAULT_CHARSET);
        response.getOutputStream().write(data);

        return null;
    }


}
