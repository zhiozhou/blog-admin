package priv.zhou.module.system.dict.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.common.domain.dto.Page;
import priv.zhou.common.domain.vo.ListVO;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.module.system.dict.domain.dao.DictDAO;
import priv.zhou.module.system.dict.domain.dto.DictDTO;
import priv.zhou.module.system.dict.domain.dto.DictDataDTO;
import priv.zhou.module.system.dict.domain.po.DictDataPO;
import priv.zhou.module.system.dict.domain.po.DictPO;
import priv.zhou.module.system.dict.service.IDictService;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;


/**
 * 字典 服务层实现
 *
 * @author zhou
 * @since 2020.04.17
 */
@Service
public class DictServiceImpl implements IDictService {

    private final DictDAO dictDAO;

    private final Integer NORMAL_TYPE = 0;

    public DictServiceImpl(DictDAO dictDAO) {
        this.dictDAO = dictDAO;
    }

    @Override
    public OutVO<NULL> save(DictDTO dictDTO) {

        // 1.转换类型
        DictPO dictPO = dictDTO.toPO();

        // 2.验证参数
        if (dictDAO.count(new DictDTO().setName(dictPO.getName())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictDTO().setKey(dictPO.getKey())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }

        // 3.补充参数
        dictPO.setCreateId(ShiroUtil.getUserId());
        if(dictDAO.save(dictPO)<1){
            OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }
        if(null != dictPO.getDataList() && !dictPO.getDataList().isEmpty()){
            dictDAO.saveData(dictPO);
        }
        return OutVO.success();


    }

    @Override
    public OutVO<NULL> remove(DictDTO dictDTO) {
        if (StringUtils.isBlank(dictDTO.getKey())) {
            return OutVO.fail(OutVOEnum.EMPTY_PARAM);
        }
        dictDAO.remove(dictDTO);
        dictDAO.removeData(dictDTO);
        return OutVO.success();
    }

    @Override
    public OutVO<NULL> update(DictDTO dictDTO) {


        // 1.转换类型
        DictPO dictPO = dictDTO.toPO();

        // 2.验证参数
        if (dictDAO.count(new DictDTO().setName(dictPO.getName()).setNoid(dictPO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_NAME);
        } else if (dictDAO.count(new DictDTO().setKey(dictPO.getKey()).setNoid(dictPO.getId())) > 0) {
            return OutVO.fail(OutVOEnum.EXIST_KEY);
        }


        // 3.补充参数
        dictPO.setModifiedId(ShiroUtil.getUserId());


        // 4.修改key值后，将数据也删除
        DictPO db = dictDAO.get(new DictDTO().setId(dictPO.getId()));
        if (!db.getKey().equals(dictPO.getKey())) {
            dictDAO.removeData(new DictDTO().setKey(db.getKey()));
        }

        if(dictDAO.update(dictPO)<1){
            OutVO.fail(OutVOEnum.FAIL_OPERATION);
        }

        dictDAO.removeData(dictDTO);
        if(null != dictPO.getDataList() && !dictPO.getDataList().isEmpty()){
            dictDAO.saveData(dictPO);
        }
        return OutVO.success();
    }


    @Override
    public OutVO<DictDTO> get(DictDTO dictDTO) {
        DictPO dictPO = dictDAO.get(dictDTO);
        if (isNull(dictPO)) {
            return new OutVO<>(OutVOEnum.EMPTY_DATA);
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

        // 2.获取数据
        List<DictDataPO> poList = dictDAO.listData(dictDTO);
        if (isNull(poList) || poList.isEmpty()) {
            return OutVO.success(Maps.newHashMap());
        }

        // 3.以code映射kv形式
        Map<String, DictDataDTO> map = Maps.newLinkedHashMap();
        for (DictDataPO po : poList) {
            map.put(po.getCode(), new DictDataDTO(po));
        }
        return OutVO.success(map);
    }

    @Override
    public OutVO<List<DictDataDTO>> dataList(DictDTO dictDTO, boolean noSystem) {
        // 1.只获取非系统字典
        if (noSystem) {
            dictDTO.setDataType(NORMAL_TYPE);
        }
        List<DictDataPO> poList = dictDAO.listData(dictDTO);
        return OutVO.success(DTO.ofPO(poList, DictDataDTO::new));
    }

    @Override
    public OutVO<Integer> count(DictDTO dictDTO) {
        return OutVO.success(dictDAO.count(dictDTO));
    }
}
