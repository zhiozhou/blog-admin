package priv.zhou.module.system.user.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 用户 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.20
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserPO implements Serializable {


    public static final long serialVersionUID = SERIAL_VERSION_UID;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

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

    /**
     * 删除位
     */
    private Boolean deleted;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
