package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.impl;

import lombok.RequiredArgsConstructor;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.vo.Result;
import ${app.packet}.common.param.NULL;
import ${app.packet}.common.service.BaseService;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.common.param.ResultEnum;
import ${app.packet}.common.domain.dto.DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dao.${table.className}DAO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.service.I${table.className}Service;
import static java.util.Objects.isNull;
import java.util.List;


/**
 * ${table.comment} 服务层实现
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Service
@RequiredArgsConstructor
public class ${table.className}ServiceImpl extends BaseServic implements I${table.className}Service {

    private final ${table.className}DAO ${table.objectName}DAO;

    @Override
    public Result<NULL> save(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DTO.toPO();
        return ${table.objectName}DAO.save(${table.objectName}PO) > 0 ?
                Result.success():
                Result.fail(ResultEnum.FAIL_OPERATION);

    }

    @Override
    public Result<NULL> remove(${table.className}DTO ${table.objectName}DTO) {
        if (null == ${table.objectName}DTO.getId()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        }
        return  ${table.objectName}DAO.remove(${table.objectName}DTO) > 0 ?
            Result.success():
            Result.fail(ResultEnum.FAIL_OPERATION);
    }

    @Override
    public Result<NULL> update(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DTO.toPO();
        return  ${table.objectName}DAO.update(${table.objectName}PO) > 0 ?
                Result.success():
                Result.fail(ResultEnum.FAIL_OPERATION);

    }


    @Override
    public Result<${table.className}DTO> get(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DAO.get(${table.objectName}DTO);
        return Result.success(new ${table.className}DTO(${table.objectName}PO));
    }

    @Override
    public Result<List<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(${table.objectName}DAO.list(${table.objectName}DTO),${table.className}DTO::new));
    }
}
