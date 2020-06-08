package priv.zhou.module.access.visitor.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import priv.zhou.module.access.visitor.domain.po.VisitorPO;
import priv.zhou.common.domain.dto.DTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 访客 数据传输模型
 *
 * @author zhou
 * @since 2020.06.08
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorDTO extends DTO<VisitorPO> {

    /**
     * 
     */
    private Integer id;

    /**
     * 名称
     */
    private String nickname;

    private String nicknameLike;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 站点
     */
    private String website;

    /**
     * 状态
     */
    private Integer state;

    private String stateStr;

    /**
     * 通知
     */
    private Integer notify;

    /**
     * 备注
     */
    private String remark;

    private String remarkLike;

    /**
     * 最后访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastAccessTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;


    public VisitorDTO(VisitorPO visitorPO) {
        super(visitorPO);
    }

}
