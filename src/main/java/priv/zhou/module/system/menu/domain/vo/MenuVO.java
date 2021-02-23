package priv.zhou.module.system.menu.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Tree;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class MenuVO extends Tree implements Serializable {

    private String parentName;

    private String icon;

    private String path;

    private Integer type;

    private Integer state;

    private Integer sort;

    private String key;
}
