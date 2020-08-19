package priv.zhou.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
public class Module {

    private String name;

    private String permissionPrefix;

    public Module() {
    }

    public Module(String name, String permissionPrefix) {
        this.name = name;
        this.permissionPrefix = permissionPrefix;
    }
}
