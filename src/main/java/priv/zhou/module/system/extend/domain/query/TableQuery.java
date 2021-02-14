package priv.zhou.module.system.extend.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class TableQuery {


    private String nameLike;

    private List<String> inNames;

}
