package priv.zhou.module.system.role.domain.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import priv.zhou.common.tools.DateUtil;

/**
 * 角色 表格渲染模型
 *
 * @author zhou
 * @since 2021.02.19
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleTableVO {

	private Integer id;

	/**
	 * key
	 */
	private String key;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 状态 关联字典
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

}
