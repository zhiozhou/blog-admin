package priv.zhou.module.blog.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UID;

/**
 * 博客类型 数据持久化模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Getter
@Setter
@Accessors(chain = true)
public class TagPO implements Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UID;

    /**
     *
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 计数
     */
    private Integer count;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TagPO) {
            TagPO tag = (TagPO) obj;
            return this.id != null && this.id.equals(tag.getId()) ||
                    this.name != null && this.name.equals(tag.getName());
        }
        return false;
    }
}
