package priv.zhou.module.system.extend.domain.po;

import lombok.Getter;
import lombok.Setter;

/**
 * 列信息
 */
@Getter
@Setter
public class ColumnPO {


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
     * 描述
     */
    private String comment;

    /**
     * 可以为空
     */
    private Boolean nullable;
}
