package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.query;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.experimental.Accessors;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import ${app.packet}.common.tools.DateUtil;



/**
 * ${table.comment} 数据查询模型
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Getter
@Setter
@Accessors(chain = true)
public class ${table.className}Query {

#foreach ($column in $table.columnList)

    /**
     * ${column.comment}
     */
#if($column.javaType=="Date")
    @DateTimeFormat(pattern = DateUtil.YMD)
#end
    private ${column.javaType} ${column.attrName};
#end

}
