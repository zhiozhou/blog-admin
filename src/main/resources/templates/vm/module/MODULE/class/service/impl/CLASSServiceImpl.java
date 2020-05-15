package ${app.packet}.module.${app.module}.${table.objectName}.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import ${app.packet}.common.domain.dto.Page;
import ${app.packet}.common.domain.vo.OutVO;
import ${app.packet}.common.param.NULL;
import ${app.packet}.common.domain.vo.ListVO;
import ${app.packet}.common.param.OutVOEnum;
import ${app.packet}.common.domain.dto.DTO;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.dao.${table.className}DAO;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.dto.${table.className}DTO;
import ${app.packet}.module.${app.module}.${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.module.${app.module}.${table.objectName}.service.I${table.className}Service;
import static java.util.Objects.isNull;
import java.util.List;


/**
 * ${table.comment} 服务层实现
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Service
public class ${table.className}ServiceImpl implements I${table.className}Service {

    private ${table.className}DAO ${table.objectName}DAO;

    public ${table.className}ServiceImpl(${table.className}DAO ${table.objectName}DAO) {
        this.${table.objectName}DAO = ${table.objectName}DAO;
    }

    @Override
    public OutVO<NULL> save(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DTO.toPO();
        return ${table.objectName}DAO.save(${table.objectName}PO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }

    @Override
    public OutVO<NULL> remove(${table.className}DTO ${table.objectName}DTO) {
        if (isNull(${table.objectName}DTO.getId())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }

        ${table.objectName}DAO.remove(${table.objectName}DTO);
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DTO.toPO();
        return  ${table.objectName}DAO.update(${table.objectName}PO) > 0 ?
                OutVO.success():
                OutVO.fail(OutVOEnum.FAIL_OPERATION);

    }


    @Override
    public OutVO<${table.className}DTO> get(${table.className}DTO ${table.objectName}DTO) {

        ${table.className}PO ${table.objectName}PO = ${table.objectName}DAO.get(${table.objectName}DTO);
        return OutVO.success(new ${table.className}DTO(${table.objectName}PO));
    }

    @Override
    public OutVO<ListVO<${table.className}DTO>> list(${table.className}DTO ${table.objectName}DTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<${table.className}PO> poList = ${table.objectName}DAO.list(${table.objectName}DTO);
        PageInfo<${table.className}PO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList,${table.className}DTO::new),  pageInfo.getTotal());
    }

    @Override
    public OutVO<Integer> count(${table.className}DTO ${table.objectName}DTO) {
        return OutVO.success(${table.objectName}DAO.count(${table.objectName}DTO));
    }
}
