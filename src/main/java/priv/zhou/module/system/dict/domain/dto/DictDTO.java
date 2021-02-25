package priv.zhou.module.system.dict.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.constant.Update;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 字典 数据传输模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictDTO {

    @NotNull(message = "ID不可为空", groups = Update.class)
    private Integer id;

    @NotBlank(message = "名称不可为空")
    private String name;

    @NotBlank(message = "字典标识不可为空")
    private String key;

    @NotNull(message = "状态不可为空")
    private Integer state;

    private String remark;

    @Valid
    @NotNull(message = "数据项不可为空")
    private List<DictDataDTO> dataList;

}