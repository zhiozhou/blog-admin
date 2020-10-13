package priv.zhou.module.system.dict.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.dict.domain.po.DictDataPO;

/**
 * 字典数据 数据传输模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class DictDataDTO extends DTO<DictDataPO> {

    /**
     * 标识码
     */
    private String code;

    /**
     * 标签
     */
    private String label;

    /**
     * 字典标识
     */
    private String dictKey;

    /**
     * 默认 0正常 1预留 11系统状态
     */
    private Integer type;

    /**
     * 默认 0正常 1默认
     */
    private Integer top;

    /**
     * 备用
     */
    private String spare;

    public DictDataDTO(DictDataPO dictDataPO) {
        super(dictDataPO);
    }

}