package priv.zhou.module.system.dict.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.misc.NULL;
import priv.zhou.common.misc.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.GlobalException;
import priv.zhou.module.system.dict.domain.dao.DictDAO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.domain.po.DictPO;
import priv.zhou.module.system.dict.service.IDictService;

import java.util.List;

import static priv.zhou.common.misc.Const.BS_DICT_DATA_KEY;
import static priv.zhou.common.misc.Const.BS_DICT_DATA_MODIFIED_KEY;


/**
 * 字典 服务层实现
 *
 * @author zhou
 * @since 2020.04.17
 */
@Service
public class DictServiceImpl extends BaseService implements IDictService {

    private final DictDAO dictDAO;

    public DictServiceImpl(DictDAO dictDAO) {
        this.dictDAO = dictDAO;
    }

    @Override
    @Transactional
    public Result<NULL> save(DictDTO dictDTO) {

        // 1.转换类型
        DictPO dictPO = dictDTO.toPO();

        // 2.验证参数
        if (null == dictPO.getDataList() || dictPO.getDataList().isEmpty()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        } else if (dictDAO.count(new DictDTO().setName(dictPO.getName())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictDTO().setKey(dictPO.getKey())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        // 3.补充参数
        dictPO.setCreateId(ShiroUtil.getUserId());

        // 4.保存字典
        if (dictDAO.save(dictPO) < 1) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        } else if (dictDAO.saveData(dictPO) < 1) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        }
        return Result.success();


    }

    @Override
    @Transactional
    public Result<NULL> remove(DictDTO dictDTO) {
        if (StringUtils.isBlank(dictDTO.getKey())) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        } else if (dictDAO.remove(dictDTO) < 0) {
            return Result.fail(ResultEnum.FAIL_OPERATION);
        } else if (dictDAO.removeData(dictDTO) < 0) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<NULL> update(DictDTO dictDTO) {


        // 1.验证参数
        if (null == dictDTO.getDataList() || dictDTO.getDataList().isEmpty()) {
            return Result.fail(ResultEnum.EMPTY_PARAM);
        } else if (dictDAO.count(new DictDTO().setName(dictDTO.getName()).setExclId(dictDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictDTO().setKey(dictDTO.getKey()).setExclId(dictDTO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        // 3.补充参数
        DictPO dictPO = dictDTO.toPO().setModifiedId(ShiroUtil.getUserId());


        // 4.修改字典
        DictPO dbPO = dictDAO.get(new DictDTO().setId(dictPO.getId()));
        if (dictDAO.update(dictPO) < 1) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        } else if (dictDAO.removeData(new DictDTO().setKey(dbPO.getKey())) < 1 || dictDAO.saveData(dictPO) < 1) {
            throw new GlobalException(ResultEnum.FAIL_OPERATION);
        } else if (DICT_SNS_KEY.equals(dbPO.getKey())) {
            RedisUtil.delete(BS_DICT_DATA_KEY + dictDTO.getKey());
            RedisUtil.delete(BS_DICT_DATA_MODIFIED_KEY + dictDTO.getKey());
        }
        return Result.success();
    }


    @Override
    public Result<DictDTO> get(DictDTO dictDTO) {
        DictPO dictPO = dictDAO.get(dictDTO);
        if (null == dictPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        }
        return Result.success(new DictDTO(dictPO));
    }

    @Override
    public Result<DictDataDTO> getData(DictDataDTO dictDataDTO) {
        return Result.success(new DictDataDTO(dictDAO.getData(dictDataDTO)));
    }


    @Override
    public Result<List<DictDTO>> list(DictDTO dictDTO, Page page) {
        startPage(page);
        return Result.success(DTO.ofPO(dictDAO.list(dictDTO), DictDTO::new));
    }


    @Override
    public List<DictDataDTO> listData(String dictKey, boolean all) {
        DictDataDTO dto = new DictDataDTO().setDictKey(dictKey);
        if (!all) {
            dto.setType(PUBLIC_TYPE);
        }
        return DTO.ofPO(dictDAO.listData(dto), DictDataDTO::new);
    }

}
