package priv.zhou.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Module {

    private String name;

    private String permissionPrefix;

    public static Module build(String name, String permissionPrefix) {
        return new Module().setName(name)
                .setPermissionPrefix(permissionPrefix);
    }

}
