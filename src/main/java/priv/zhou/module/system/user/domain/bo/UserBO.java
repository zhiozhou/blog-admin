package priv.zhou.module.system.user.domain.bo;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.role.domain.po.RolePO;
import priv.zhou.module.system.role.domain.vo.RoleVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

/**
 * 用户 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserBO implements Serializable {


    public static final long serialVersionUID = SERIAL_VERSION_UOD;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态 关联字典
     */
    private Integer state;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人
     */
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    private Date gmtModified;


    private List<RolePO> roles;
}
