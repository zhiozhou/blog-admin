package priv.zhou.module.test.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.interfaces.Save;
import priv.zhou.common.interfaces.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class TestDTO {

    @NotNull(message = "id不可为空", groups = Update.class)
    private Integer id;

    @NotBlank(message = "姓名不可为空")
    private String name;

    @NotNull(message = "公司不可为空", groups = Save.class)
    private Integer companyId;

}
