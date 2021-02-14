package priv.zhou.module.system.extend.domain.bo;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

import static priv.zhou.module.system.extend.service.IExtendService.PRIMARY;

/**
 * @author zhou
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TableBO {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

    /**
     * 类名(第一个字母大写)
     */
    private String className;

    /**
     * 类名(第一个字母小写)
     */
    private String objectName;

    /**
     * 表的主键列信息
     */
    private List<ColumnBO> primaryKeys;

    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnBO> columnList;


    public TableBO setComment(String comment) {
        if (null != comment) {
            this.comment = comment.replaceAll("(?:表|信息)", "");
        }
        return this;
    }

    public TableBO setName(String name) {
        this.name = name;
        setClassName(name);
        return this;
    }

    public TableBO setClassName(String name) {
        this.className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
        this.objectName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
        return this;
    }

    /**
     * 设置列时,同时设置主键
     */
    public void setColumnList(List<ColumnBO> columnList) {
        this.columnList = columnList;
        this.primaryKeys = columnList.stream().filter(column -> PRIMARY.equals(column.getKey())).collect(Collectors.toList());
    }

}
