package priv.zhou.module.system.dict.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.dict.domain.dao.DictDAO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.domain.po.DictPO;
import priv.zhou.module.system.dict.service.IDictService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static priv.zhou.common.param.CONSTANT.BS_DICT_DATA_KEY;
import static priv.zhou.common.param.CONSTANT.BS_DICT_DATA_MODIFIED_KEY;


/**
 * 字典 服务层实现
 *
 * @author zhou
 * @since 2020.04.17
 */
@Service
public class DictServiceImpl implements IDictService {

    private final DictDAO dictDAO;

    public DictServiceImpl(DictDAO dictDAO) {
        this.dictDAO = dictDAO;
    }

    @Override
    @Transactional
    public OutVO<NULL> save(DictDTO dictDTO) {

        // 1.转换类型
        DictPO dictPO = dictDTO.toPO();

        // 2.验证参数
        if (null == dictPO.getDataList() || dictPO.getDataList().isEmpty()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if (dictDAO.count(new DictDTO().setName(dictPO.getName())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictDTO().setKey(dictPO.getKey())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 3.补充参数
        dictPO.setCreateId(ShiroUtil.getUserId());

        // 4.保存字典
        if (dictDAO.save(dictPO) < 1) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        } else if (dictDAO.saveData(dictPO) < 1) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        }
        return OutVO.success();


    }

    @Override
    @Transactional
    public OutVO<NULL> remove(DictDTO dictDTO) {
        if (StringUtils.isBlank(dictDTO.getKey())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if (dictDAO.remove(dictDTO) < 0) {
            return OutVO.fail(OutVOEnum.FAIL_OPERATION);
        } else if (dictDAO.removeData(dictDTO) < 0) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        }
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(DictDTO dictDTO) {


        // 1.转换类型
        DictPO dictPO = dictDTO.toPO();

        // 2.验证参数
        if (null == dictPO.getDataList() || dictPO.getDataList().isEmpty()) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        } else if (dictDAO.count(new DictDTO().setName(dictPO.getName()).setNoid(dictPO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictDTO().setKey(dictPO.getKey()).setNoid(dictPO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 3.补充参数
        dictPO.setModifiedId(ShiroUtil.getUserId());

        // 4.修改key值后，将数据也删除
        DictPO dbPO = dictDAO.get(new DictDTO().setId(dictPO.getId()));
        if (!dbPO.getKey().equals(dictPO.getKey()) && dictDAO.removeData(new DictDTO().setKey(dbPO.getKey())) < 1) {

            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        }

        // 5.修改字典
        if (dictDAO.update(dictPO) < 1) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        } else if (dictDAO.removeData(dictDTO) < 1 || dictDAO.saveData(dictPO) < 1) {
            throw new GlobalException().setOutVO(OutVO.fail(OutVOEnum.FAIL_OPERATION));
        } else if (DICT_SNS_KEY.equals(dictDTO.getKey())) {
            RedisUtil.delete(BS_DICT_DATA_KEY + dictDTO.getKey());
            RedisUtil.delete(BS_DICT_DATA_MODIFIED_KEY + dictDTO.getKey());
        }
        return OutVO.success();
    }


    @Override
    public OutVO<DictDTO> get(DictDTO dictDTO) {
        DictPO dictPO = dictDAO.get(dictDTO);
        if (isNull(dictPO)) {
            return OutVO.fail(OutVOEnum.EMPTY_DATA);
        }
        return OutVO.success(new DictDTO(dictPO));
    }

    @Override
    public OutVO<DictDataDTO> getData(DictDTO dictDTO) {
        return OutVO.success(new DictDataDTO(dictDAO.getData(dictDTO)));
    }


    @Override
    public OutVO<ListVO<DictDTO>> list(DictDTO dictDTO, Page page) {
        PageHelper.startPage(page.getPage(), page.getLimit(), page.isCount());
        List<DictPO> poList = dictDAO.list(dictDTO);
        PageInfo<DictPO> pageInfo = new PageInfo<>(poList);
        return OutVO.list(DTO.ofPO(poList, DictDTO::new), pageInfo.getTotal());
    }

    @Override
    public OutVO<Map<String, DictDataDTO>> dataMap(DictDTO dictDTO, boolean noSystem) {

        // 1.只获取非系统字典
        if (noSystem) {
            dictDTO.setDataType(NORMAL_TYPE);
        }
        return OutVO.success(dictDAO.listData(dictDTO).stream()
                .map(DictDataDTO::new)
                .collect(Collectors.toMap(DictDataDTO::getCode, dto -> dto)));
    }

    @Override
    public OutVO<List<DictDataDTO>> dataList(DictDTO dictDTO, boolean noSystem) {
        // 1.只获取非系统字典
        if (noSystem) {
            dictDTO.setDataType(NORMAL_TYPE);
        }
        return OutVO.success(DTO.ofPO(dictDAO.listData(dictDTO), DictDataDTO::new));
    }

}
