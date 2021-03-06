package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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

}
