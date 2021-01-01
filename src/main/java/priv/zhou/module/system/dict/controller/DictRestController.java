package priv.zhou.module.system.dict.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.TableVO;
import priv.zhou.common.misc.NULL;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.service.IDictService;

import javax.validation.Valid;

/**
 * 字典 控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@RestController
@RequestMapping("/system/dict/rest")
public class DictRestController {

    private final IDictService dictService;

    public DictRestController(IDictService dictService) {
        this.dictService = dictService;
    }

    @RequiresPermissions("system:dict:add")
    @RequestMapping("/save")
    public Result<NULL> save(@Valid DictDTO dictDTO) {
        return dictService.save(dictDTO);
    }

    @RequiresPermissions("system:dict:remove")
    @RequestMapping("/remove")
    public Result<NULL> remove(DictDTO dictDTO) {
        return dictService.remove(dictDTO);
    }

    @RequiresPermissions("system:dict:update")
    @RequestMapping("/update")
    public Result<NULL> update(@Valid DictDTO dictDTO) {
        return dictService.update(dictDTO);
    }

    @RequiresPermissions("system:dict:list")
    @RequestMapping("/list")
    public Result<TableVO<DictDTO>> list(DictDTO dictDTO, Page page) {
        return Result.table(dictService.list(dictDTO, page));
    }


}
