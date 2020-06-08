package priv.zhou.module.access.accessLog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.access.accessLog.domain.po.AccessLogPO;
import priv.zhou.module.access.visitor.domain.dto.VisitorDTO;

import java.util.Date;


/**
 * 访问日志 数据传输模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessLogDTO extends DTO<AccessLogPO> {


    /**
     *
     */
    private Integer id;

    /**
     * 访客
     */
    private VisitorDTO visitor;

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

    private String apiLike;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;


    public AccessLogDTO(AccessLogPO accessLogPO) {
        super(accessLogPO);
        this.visitor = ofPO(accessLogPO.getVisitor(), VisitorDTO::new);
    }

}
