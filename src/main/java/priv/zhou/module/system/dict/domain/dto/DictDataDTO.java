package priv.zhou.module.system.dict.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 字典数据 数据传输模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictDataDTO {

    @NotBlank(message = "标识码不可为空")
    private String code;

    @NotBlank(message = "标签不可为空")
    private String label;

    @NotBlank(message = "字典标识不可为空")
    private String dictKey;

    @NotNull(message = "数据类型不可为空")
    private Integer type;

    @NotNull(message = "默认标识不可为空")
    private Integer top;

    private String spare;

}