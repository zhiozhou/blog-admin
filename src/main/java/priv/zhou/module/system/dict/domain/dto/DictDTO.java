package priv.zhou.module.system.dict.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.dict.domain.po.DictPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;


/**
 * 字典 数据传输模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictDTO extends DTO<DictPO> {


    /**
     * id
     */
    private Integer id;

    /**
     * 非id
     */
    private Integer exclId;

    /**
     * 名称
     */
    @NotBlank(message = "名称不可为空")
    private String name;

    /**
     * 标示值
     */
    private String code;

    /**
     * 名称
     */
    private String nameLike;

    /**
     * 字典标识
     */
    @NotBlank(message = "字典标识不可为空")
    private String key;


    /**
     * 字典标识like
     */
    private String keyLike;

    /**
     * 状态 0正常 11停用
     */
    @NotNull(message = "状态不可为空")
    private Integer state;

    /**
     * 数据项状态
     */
    private Integer dataType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据列表
     */
    private List<DictDataDTO> dataList;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 修改人
     */
    private Integer modifiedId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


    public DictDTO setCode(Integer code) {
        this.code = code.toString();
        return this;
    }

    public DictDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public DictDTO(DictPO dictPO) {
        super(dictPO);
        this.dataList = ofPO(dictPO.getDataList(), DictDataDTO::new);
    }
}