package priv.zhou.common.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Module {

    private String name;

    private String permissionPrefix;

    public Module(String name, String permissionPrefix) {
        this.name = name;
        this.permissionPrefix = permissionPrefix;
    }
}
