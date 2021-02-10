package priv.zhou.module.system.user.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class UserSaveDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不可为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不可为空")
    private String password;

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
