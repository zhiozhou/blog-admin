package ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import ${app.packet}.module.$!{app.moduleRef}${table.objectName}.domain.po.${table.className}PO;
import ${app.packet}.common.domain.dto.DTO;
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
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ${table.className}DTO extends DTO<${table.className}PO> {

#foreach ($column in $table.columnList)

    /**
     * ${column.comment}
     */
#if($column.javaType == "Date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
#end
#if($column.name!=$table.primaryKey.name && !$column.nullable)
#if($column.javaType=="String")
    @NotBlank(message = "${column.comment}不可为空")
#end
#end
    private ${column.javaType} ${column.attrName};
#end


    public ${table.className}DTO(${table.className}PO ${table.objectName}PO) {
        super(${table.objectName}PO);
    }

}
