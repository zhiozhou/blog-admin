package priv.zhou.module.system.menu.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.Tree;

import java.io.Serializable;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

@Getter
@Setter
@Accessors(chain = true)
public class MenuSelectVO extends Tree implements Serializable {

    public static final long serialVersionUID = SERIAL_VERSION_UOD;

    private Integer type;
}
