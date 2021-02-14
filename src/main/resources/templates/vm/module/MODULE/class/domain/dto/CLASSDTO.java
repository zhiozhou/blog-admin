package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto;

import ${app.packet}.common.tools.DateUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;


/**
 * ${table.comment} 数据传输模型
 *
 * @author ${app.author}
 * @since ${app.since}
 */
@Getter
@Setter
@Accessors(chain = true)
public class ${table.className}DTO {

#foreach ($column in $table.columnList)

    /**
     * ${column.comment}
     */
#if(!$table.primaryKeys.contains($column))
#if($column.javaType=="String")
    @NotBlank(message = "${column.comment}不可为空")
#else
#if($column.javaType=="Date")
    @DateTimeFormat(pattern = DateUtil.YMD)
#end
    @NotNull(message = "${column.comment}不可为空")
#end
#end
    private ${column.javaType} ${column.attrName};
#end

}
