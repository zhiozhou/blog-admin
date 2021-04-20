package priv.zhou.module.block.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.user.domain.po.UserPO;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 阻塞 数据持久化模型
 *
 * @author zhou
 * @since 2020.09.18
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlockPO implements Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UID;

    /**
     *
     */
    private Integer id;

    /**
     * IP
     */
    private String ip;

    private String ipLike;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 原因
     */
    private String reason;

    private String reasonLike;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private UserPO creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 释放时间
     */
    private Date gmtFreed;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
