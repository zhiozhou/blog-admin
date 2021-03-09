package priv.zhou.module.blog.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

import static priv.zhou.common.constant.GlobalConst.SERIAL_VERSION_UOD;

/**
 * 博客 数据持久化模型
 *
 * @author zhou
 * @since 2021.03.07
 */
@Getter
@Setter
@Accessors(chain = true)
public class BlogPO implements Serializable{

	public static final long serialVersionUID = SERIAL_VERSION_UOD;

	/**
	 * 
	 */
	private Integer id;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * 预览
	 */
	private String preview;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 摘要
	 */
	private String abs;

	/**
	 * 页面访问量
	 */
	private Long pv;

	/**
	 * 创建人
	 */
	private Integer createBy;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改人
	 */
	private Integer modifiedBy;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	/**
	 * 删除位
	 */
	private Boolean deleted;

}
