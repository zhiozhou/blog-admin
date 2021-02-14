package priv.zhou.module.system.monitor.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.tools.DateUtil;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionVO {

    @NotEmpty(message = "id不可为空")
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色名称
     */
    private String roleNames;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * ip地址
     */
    private String host;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = DateUtil.YMDHMS, timezone = DateUtil.TIME_ZONE)
    private Date loginTime;

    /**
     * 最后访问时间
     */
    @JsonFormat(pattern = DateUtil.YMDHMS, timezone = DateUtil.TIME_ZONE)
    private Date lastAccessTime;


}
