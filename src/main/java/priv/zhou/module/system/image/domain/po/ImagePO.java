package priv.zhou.module.system.image.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典 数据持久化模型
 *
 * @author zhou
 * @since 2020.06.01
 */
@Getter
@Setter
@Accessors(chain = true)
public class ImagePO implements Serializable {

    private Integer id;

    /**
     * 地址
     */
    private String url;

    /**
     * 缩略图地址
     */
    private String thumbnailUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

}
