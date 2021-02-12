package priv.zhou.module.system.user.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 用户 数据传输模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserUpdateDTO {

    private Integer id;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不可为空")
    private String name;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不可为空")
    private String phone;

    /**
     * 角色
     */
    @NotNull(message = "角色不可为空")
    private List<Integer> roles;

}
