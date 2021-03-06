package priv.zhou.module.system.dict.service.impl;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.service.BaseService;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.RestException;
import priv.zhou.module.system.dict.domain.dao.DictDAO;
import priv.zhou.module.system.dict.domain.dao.DictDataDAO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.domain.po.DictDataPO;
import priv.zhou.module.system.dict.domain.po.DictPO;
import priv.zhou.module.system.dict.domain.query.DictDataQuery;
import priv.zhou.module.system.dict.domain.query.DictQuery;
import priv.zhou.module.system.dict.domain.vo.DictDataVO;
import priv.zhou.module.system.dict.domain.vo.DictTableVO;
import priv.zhou.module.system.dict.domain.vo.DictVO;
import priv.zhou.module.system.dict.service.IDictService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static priv.zhou.common.constant.RedisConst.BS_DICT_DATA_KEY;
import static priv.zhou.common.constant.RedisConst.BS_DICT_DATA_MODIFIED_KEY;


/**
 * 字典 服务层实现
 *
 * @author zhou
 * @since 2020.04.17
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends BaseService implements IDictService {

    private final DictDAO dictDAO;

    private final DictDataDAO dictDataDAO;

    @Override
    @Transactional
    public Result<NULL> save(DictDTO dictDTO) {

        if (dictDAO.count(new DictQuery().setName(dictDTO.getName())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictQuery().setKey(dictDTO.getKey())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        }

        DictPO dictPO = new DictPO()
                .setKey(dictDTO.getKey())
                .setName(dictDTO.getName())
                .setGmtCreate(new Date())
                .setState(dictDTO.getState())
                .setCreateBy(ShiroUtil.getUserId());

        // 4.保存字典
        if (dictDAO.save(dictPO) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (dictDataDAO.saveList(dictDTO.getDataList()
                .stream()
                .map(dataDTO -> new DictDataPO()
                        .setPerf(dataDTO.getPerf())
                        .setCode(dataDTO.getCode())
                        .setType(dataDTO.getType())
                        .setLabel(dataDTO.getLabel())
                        .setSpare(dataDTO.getSpare())
                        .setDictKey(dictDTO.getKey()))
                .collect(Collectors.toList())) < 1) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
        return Result.success();


    }

    @Override
    @Transactional
    public Result<NULL> remove(List<Integer> ids) {
        for (Integer id : ids) {
            DictPO dictPO = dictDAO.get(new DictQuery().setId(id));
            if (null == dictPO) {
                return Result.fail(ResultEnum.EMPTY_DATA);
            } else if (dictDAO.remove(id) < 0) {
                throw new RestException(ResultEnum.LATER_RETRY);
            } else if (dictDataDAO.remove(dictPO.getKey()) < 0) {
                throw new RestException(ResultEnum.LATER_RETRY);
            }
        }
        return Result.success();
    }


    @Override
    @Transactional
    public Result<NULL> update(DictDTO dictDTO) {

        if (dictDTO.getDataList().stream()
                .map(DictDataDTO::getCode)
                .distinct().count() != dictDTO.getDataList().size()) {
            return Result.fail(ResultEnum.REPEAT_KEY);
        }

        DictPO dictPO = dictDAO.get(new DictQuery().setId(dictDTO.getId()));
        if (null == dictPO) {
            return Result.fail(ResultEnum.EMPTY_DATA);
        } else if (!dictPO.getName().equals(dictDTO.getName()) &&
                dictDAO.count(new DictQuery().setName(dictDTO.getName()).setRidId(dictPO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_NAME);
        } else if (!dictPO.getKey().equals(dictDTO.getKey()) &&
                dictDAO.count(new DictQuery().setKey(dictDTO.getKey()).setRidId(dictPO.getId())) > 0) {
            return Result.fail(ResultEnum.EXIST_KEY);
        } else if (dictDAO.update(new DictPO()
                .setKey(dictDTO.getKey())
                .setName(dictDTO.getName())
                .setRemark(dictDTO.getRemark())
                .setModifiedBy(ShiroUtil.getUserId())) < 1) {
            return Result.fail(ResultEnum.LATER_RETRY);
        } else if (dictDataDAO.delete(dictDTO.getKey()) < 1 ||
                dictDataDAO.saveList(dictDTO.getDataList().stream()
                        .map(data -> new DictDataPO()
                                .setDictKey(dictDTO.getKey())
                                .setPerf(data.getPerf())
                                .setCode(data.getCode())
                                .setType(data.getType())
                                .setLabel(data.getLabel())
                                .setSpare(data.getSpare()))
                        .collect(Collectors.toList())) != dictDTO.getDataList().size()) {
            throw new RestException(ResultEnum.LATER_RETRY);
        }
        RedisUtil.delete(Lists.newArrayList(BS_DICT_DATA_KEY + dictPO.getKey(), BS_DICT_DATA_MODIFIED_KEY + dictDTO.getKey()));
        return Result.success();
    }

    @Override
    public DictVO getVO(DictQuery query) {
        return dictDAO.getVO(query);
    }

    @Override
    public List<DictTableVO> listTableVO(DictQuery query, Page page) {
        startPage(page);
        return dictDAO.listTableVO(query);
    }


    @Override
    public DictDataPO getData(DictDataQuery query) {
        return dictDataDAO.get(query);
    }


    @Override
    public List<DictDataPO> listData(DictDataQuery query) {
        return dictDataDAO.list(query);
    }

    @Override
    public List<DictDataVO> listDataVO(DictDataQuery query) {
        return dictDataDAO.listVO(query);
    }

}
