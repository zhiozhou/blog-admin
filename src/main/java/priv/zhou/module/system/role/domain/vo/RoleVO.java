package priv.zhou.module.system.role.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.menu.domain.po.MenuPO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class RoleVO implements Serializable {

	private Integer id;

	private String name;

}
