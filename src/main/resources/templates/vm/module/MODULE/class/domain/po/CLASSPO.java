package ${app.packet}.module.${app.module}.${table.objectName}.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.Date;

/**
 * ${table.comment} 数据持久化模型
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Getter
@Setter
@Accessors(chain = true)
public class ${table.className}PO implements Serializable{

#foreach ($column in $table.columnList)

	/**
	 * ${column.comment}
	 */
	private ${column.javaType} ${column.attrName};
#end

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
