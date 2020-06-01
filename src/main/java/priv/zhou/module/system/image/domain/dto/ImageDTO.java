package priv.zhou.module.system.image.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.image.domain.po.ImagePO;

/**
 * 图片 数据传输模型
 *
 * @author zhou
 * @since 2020.06.01
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDTO extends DTO<ImagePO> {

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


    public ImageDTO(ImagePO imagePO) {
        super(imagePO);
    }
}
