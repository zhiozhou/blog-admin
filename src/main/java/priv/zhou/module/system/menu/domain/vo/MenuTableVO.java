package priv.zhou.module.system.menu.domain.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import priv.zhou.common.tools.DateUtil;

/**
 * 菜单 表格渲染模型
 *
 * @author zhou
 * @since 2021.02.20
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuTableVO {


	/**
	 * 
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

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 类型 关联字典
	 */
	private Integer type;

	/**
	 * 状态 关联字典
	 */
	private Integer state;

	/**
	 * 排序 从小直大
	 */
	private Integer sort;

	/**
	 * 权限标识
	 */
	private String key;

	/**
	 * 旗帜 0为后台 1为前台
	 */
	private Integer flag;

	/**
	 * 创建人
	 */
	private Integer createBy;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = DateUtil.YMDHMS, timezone = DateUtil.TIME_ZONE)
	private Date gmtCreate;

	/**
	 * 修改人
	 */
	private Integer modifiedBy;

	/**
	 * 修改时间
	 */
	@JsonFormat(pattern = DateUtil.YMDHMS, timezone = DateUtil.TIME_ZONE)
	private Date gmtModified;

}
