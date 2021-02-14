package priv.zhou.module.system.extend.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.tools.DateUtil;

import java.util.Date;

/**
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class TableTableVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DateUtil.YMD, timezone = DateUtil.TIME_ZONE)
    private Date gmtCreate;

}
