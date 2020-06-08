package priv.zhou.module.access.accessLog.domain.po;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.access.visitor.domain.po.VisitorPO;

import java.io.Serializable;
import java.util.Date;

/**
 * 访问日志 数据持久化模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@Accessors(chain = true)
public class AccessLogPO implements Serializable {


    /**
     *
     */
    private Integer id;

    /**
     * 访客
     */
    private VisitorPO visitor;

    /**
     * 主机
     */
    private String host;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 访问接口
     */
    private String api;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
