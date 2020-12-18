package priv.zhou.module.system.extend.domain.dto;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.common.domain.dto.DTO;
import priv.zhou.module.system.extend.domain.po.TablePO;

import java.util.List;

import static priv.zhou.common.misc.Const.PRIMARY;

/**
 * @author zhou
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class TableDTO extends DTO<TablePO> {

    /**
     * 名称
     */
    private String name;

    /**
     * 表名
     */
    private List<String> nameIn;

    /**
     * 模糊名称
     */
    private String nameLike;

    /**
     * 描述
     */
    private String comment;


    /**
     * 表的主键列信息
     */
    private ColumnDTO primaryKey;

    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnDTO> columnList;

    /**
     * 类名(第一个字母大写)
     */
    private String className;

    /**
     * 类名(第一个字母小写)
     */
    private String objectName;


    public TableDTO setComment(String comment) {
        if (null != comment) {
            this.comment = comment.replaceAll("(?:表|信息)", "");
        }
        return this;
    }

    /**
     * 设置列时,同时设置主键
     */
    public void setColumnList(List<ColumnDTO> columnList) {
        if (null != columnList) {
            this.columnList = columnList;
            this.primaryKey = columnList.stream().filter(column -> PRIMARY.equals(column.getKey())).findFirst().orElse(null);
        }
    }

    public TableDTO setName(String name) {
        this.name = name;
        setClassName(name);
        return this;
    }

    public void setClassName(String name) {
        this.className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
        this.objectName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
    }


    public TableDTO(TablePO tablePO) {
        super(tablePO);
    }
}
