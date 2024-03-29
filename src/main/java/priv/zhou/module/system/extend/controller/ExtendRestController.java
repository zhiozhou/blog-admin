package priv.zhou.module.system.extend.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.system.extend.domain.query.TableQuery;
import priv.zhou.module.system.extend.domain.vo.TableTableVO;
import priv.zhou.module.system.extend.service.IExtendService;
import priv.zhou.module.system.extend.service.ITableService;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static priv.zhou.common.constant.GlobalConst.DEFAULT_ENC;

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

    @RequiresPermissions("system:extend:view")
    @PostMapping("/table/list")
    public Result<TableVO<TableTableVO>> list(TableQuery query, Page page) {
        return Result.table(tableService.listTableVO(query, page));
    }

    @RequiresPermissions("system:extend:extend")
    @GetMapping("/extend")
    public Result<byte[]> extend(HttpServletResponse response, @RequestParam("names") List<String> names) throws Exception {

        Result<byte[]> result = extendService.generate(names);
        if (result.isFail()) {
            return result;
        }

        byte[] data = result.getData();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"extend:" + StringUtils.join(names, ",") + ".zip\"");
        response.addHeader("Content-Length", String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=" + DEFAULT_ENC);
        response.getOutputStream().write(data);

        return null;
    }


}
