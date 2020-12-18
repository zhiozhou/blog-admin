package priv.zhou.module.system.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.role.domain.dto.RoleDTO;
import priv.zhou.module.system.user.domain.po.UserPO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户 数据传输模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO extends DTO<UserPO> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 非id
     */
    private Integer exclId;


    /**
     * 用户名
     */
    @NotBlank(message = "用户名不可为空")
    private String username;

    /**
     * 用户名like
     */
    private String nameLike;

    /**
     * 密码
     */
    private String password;


    /**
     * 姓名
     */
    @NotBlank(message = "姓名不可为空")
    private String name;


    /**
     * 状态 关联字典
     */
    private Integer state;

    /**
     * 状态字符
     */
    private String stateStr;


    /**
     * 角色
     */
    @NotNull(message = "角色不可为空")
    private RoleDTO role;


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


    /**
     * 是否记住我
     */
    private Boolean rememberMe;

    public UserDTO(UserPO userPO) {
        super(userPO);
        this.role = ofPO(userPO.getRole(),RoleDTO::new);
    }


    @Override
    public UserPO toPO() {
        return super.toPO()
                .setRole(toPO(role));
    }


}
