package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.experimental.Accessors;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import ${app.packet}.common.tools.DateUtil;

/**
 * ${table.comment} 表格渲染模型
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${table.className}TableVO {

#foreach ($column in $table.columnList)

	/**
	 * ${column.comment}
	 */
#if($column.javaType=="Date")
	@JsonFormat(pattern = DateUtil.YMDHMS, timezone = DateUtil.TIME_ZONE)
#end
	private ${column.javaType} ${column.attrName};
#end

}
