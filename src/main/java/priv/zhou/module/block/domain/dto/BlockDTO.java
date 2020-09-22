package priv.zhou.module.block.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.block.domain.po.BlockPO;
import priv.zhou.module.system.user.domain.dto.UserDTO;

import java.util.Date;


/**
 * 阻塞 数据传输模型
 *
 * @author zhou
 * @since 2020.09.18
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockDTO extends DTO<BlockPO> {


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
    private UserDTO creator;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;


    public BlockDTO(BlockPO blockPO) {
        super(blockPO);
        this.creator = ofPO(blockPO.getCreator(), UserDTO::new);
    }

}
