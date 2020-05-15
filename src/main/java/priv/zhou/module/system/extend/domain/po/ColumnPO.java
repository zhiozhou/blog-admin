package priv.zhou.module.system.extend.domain.po;

import lombok.Getter;
import lombok.Setter;

/**
 * 列信息
 */
@Getter
@Setter
public class ColumnPO {

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;


    /**
     * 描述
     */
    private String comment;

}
