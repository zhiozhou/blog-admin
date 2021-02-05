package priv.zhou.module.system.dict.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.dict.domain.po.DictPO;

import javax.validation.Valid;
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
@Accessors(chain = true)
public class DictDTO{


    @NotBlank(message = "名称不可为空")
    private String name;

    @NotBlank(message = "字典标识不可为空")
    private String key;

    @NotNull(message = "状态不可为空")
    private Integer state;

    @Valid
    @NotNull(message = "数据项不可为空")
    private List<DictDataDTO> dataList;

}