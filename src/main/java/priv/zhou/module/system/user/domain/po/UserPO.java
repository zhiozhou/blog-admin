package priv.zhou.module.system.user.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.role.domain.po.RolePO;

import java.io.Serializable;
import java.util.Date;

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
     * 状态 关联字典
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

	/**
	 * 角色
	 */
    private RolePO role;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
