package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import ${app.packet}.common.tools.DateUtil;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${table.className}VO {

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
