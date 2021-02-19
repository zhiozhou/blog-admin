package priv.zhou.module.system.menu.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class MenuVO implements Serializable {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 父级id
	 */
	private Integer parentId;

	/**
	 * 名称
	 */
	private String name;

}
