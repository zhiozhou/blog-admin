package priv.zhou.module.system.extend.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class ExtendDTO {

    @NotNull(message = "至少生成一个表")
    private List<String> names;
}
