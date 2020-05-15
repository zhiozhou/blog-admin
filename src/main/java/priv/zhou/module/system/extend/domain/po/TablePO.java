package priv.zhou.module.system.extend.domain.po;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class TablePO implements Serializable {

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String comment;

}
