package priv.zhou.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 模块渲染模型
 *
 * @author zhou
 * @since 0.1.0
 */
@Getter
@Setter
@Accessors(chain = true)
public class Module {

    /**
     * 模块名称
     */
    private String name;

    /**
     * 权限前缀
     */
    private String permissionPrefix;

    public static Module build(String name, String permissionPrefix) {
        return new Module()
                .setName(name)
                .setPermissionPrefix(permissionPrefix);
    }

}
