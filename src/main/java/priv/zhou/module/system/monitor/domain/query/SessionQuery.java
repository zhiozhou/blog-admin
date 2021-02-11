package priv.zhou.module.system.monitor.domain.query;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SessionQuery {

    /**
     * 用户名
     */
    private String username;

    /**
     * ip地址
     */
    private String host;
}
