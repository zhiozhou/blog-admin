package priv.zhou.module.system.menu.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class MenuQuery {

    private Integer id;

    private Integer ridId;

    private Integer parentId;

    private Integer userId;

    private String name;

    private String key;

    private Integer state;

    private Integer flag;

    private List<Integer> inTypes;

    public MenuQuery(Integer flag) {
        this.flag = flag;
    }
}
