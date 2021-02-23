package priv.zhou.module.system.menu.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Tree;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
public class MenuSelectVO extends Tree implements Serializable {
}
