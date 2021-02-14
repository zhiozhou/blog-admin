package priv.zhou.module.system.extend.domain.bo;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static priv.zhou.module.system.extend.service.IExtendService.TYPE_MAP;

/**
 * 列信息
 */
@Getter
@Setter
@Accessors(chain = true)
public class ColumnBO {

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
     * GetSet名称(第一个字母大写)，如：user_name => UserName
     */
    private String getSetName;

    /**
     * 可以为空
     */
    private Boolean nullable;

    /**
     * 描述
     */
    private String comment;

    public ColumnBO setName(String name) {
        this.name = name;
        this.attrName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name.startsWith("is_") ? name.substring(3) : name);
        this.getSetName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, attrName);
        return this;
    }


    public ColumnBO setType(String columnType) {
        this.type = columnType;
        this.javaType = TYPE_MAP.get(columnType);
        return this;
    }

    public void setNullable(String nullable) {
        this.nullable = "YES".equals(nullable);
    }

}

