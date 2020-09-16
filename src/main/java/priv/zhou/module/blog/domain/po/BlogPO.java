package priv.zhou.module.blog.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 博客 数据持久化模型
 *
 * @author zhou
 * @since 2020.09.14
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogPO implements Serializable {


    private Integer id;


    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 预览
     */
    private String preview;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 标签列表
     */
    private Set<TagPO> tags;

    /**
     * 备注
     */
    private String remark;

    /**
     * 摘要
     */
    private String abs;

    /**
     * 页面访问量
     */
    private Long pv;

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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
