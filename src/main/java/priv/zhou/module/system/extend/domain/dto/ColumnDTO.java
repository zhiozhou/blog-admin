package priv.zhou.module.system.extend.domain.dto;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.extend.domain.po.ColumnPO;

import java.util.List;
import java.util.stream.Collectors;

import static priv.zhou.common.param.CONSTANT.TYPE_MAP;

/**
 * 列信息
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ColumnDTO extends DTO<ColumnPO> {

    /**
     * 键类型
     */
    private String key;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * Java类型
     */
    private String javaType;

    /**
     * Java属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrName;

    /**
     * 可以为空
     */
    private Boolean nullable;

    /**
     * 描述
     */
    private String comment;


    /**
     * 表名称
     */
    private String tableName;


    public ColumnDTO setName(String name) {
        this.name = name;
        this.attrName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
        return this;
    }


    public ColumnDTO setType(String columnType) {
        this.type = columnType;
        this.javaType = TYPE_MAP.get(columnType);
        return this;
    }


    public ColumnDTO(ColumnPO columnPO) {
        super(columnPO);
    }
}

