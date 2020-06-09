package priv.zhou.module.system.dict.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 字典 数据持久化模型
 *
 * @author zhou
 * @since 2020.04.17
 */
@Getter
@Setter
@Accessors(chain = true)
public class DictPO implements Serializable{


	/**
	 * id
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 字典key
	 */
	private String key;

	/**
	 * 状态 0正常 11停用
	 */
	private Integer state;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 数据列表
	 */
	private List<DictDataPO> dataList;

	/**
	 * 提交版本
	 */
	private Integer version;

	/**
	 * 创建人
	 */
	private Integer createId;

	/**
	 * 修改人
	 */
	private Integer modifiedId;

	/**
	 * 创建时间
	 */
	private Date gmtCreate;

	/**
	 * 修改时间
	 */
	private Date gmtModified;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
