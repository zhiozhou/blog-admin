package priv.zhou.module.system.dict.controller;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.query.DictQuery;
import priv.zhou.module.system.dict.domain.vo.DictTableVO;
import priv.zhou.module.system.dict.service.IDictService;

import javax.validation.Valid;

/**
 * 字典 控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/dict/rest")
public class DictRestController {

    private final IDictService dictService;

    @RequiresPermissions("system:dict:add")
    @PostMapping("/save")
    public Result<NULL> save(@Valid DictDTO dictDTO) {
        return dictService.save(dictDTO);
    }

    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    public Result<NULL> remove(@RequestParam(value = "ids[]") int[] ids) {
        return dictService.remove(ids);
    }

    @RequiresPermissions("system:dict:update")
    @PostMapping("/update/{id}")
    public Result<NULL> update(@PathVariable Integer id, @Valid DictDTO dictDTO) {
        return dictService.update(dictDTO.setId(id));
    }

    @RequiresPermissions("system:dict:list")
    @PostMapping("/list")
    public Result<TableVO<DictTableVO>> listTableVO(DictQuery query, Page page) {
        return Result.success(TableVO.build(dictService.listTableVO(query, page)));
    }


}
