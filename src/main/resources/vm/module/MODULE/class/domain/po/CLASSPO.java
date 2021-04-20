package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po;

import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import static ${app.packet}.common.constant.GlobalConst.SERIAL_VERSION_UID;

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

	public static final long serialVersionUID = SERIAL_VERSION_UID;

#foreach ($column in $table.columnList)

	/**
	 * ${column.comment}
	 */
	private ${column.javaType} ${column.attrName};
#end

}
