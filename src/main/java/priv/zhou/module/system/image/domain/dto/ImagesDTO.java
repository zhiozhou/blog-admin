package priv.zhou.module.system.image.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImagesDTO {

    private String remark;

    private List<ImageDTO> imageList;
}
