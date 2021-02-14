package priv.zhou.module.system.extend.domain.query;

import com.google.common.base.CaseFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import priv.zhou.module.system.extend.domain.dto.ColumnDTO;
import priv.zhou.module.system.extend.domain.po.TablePO;

import java.util.List;
import java.util.stream.Collectors;

import static priv.zhou.common.constant.GlobalConst.PRIMARY;

/**
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class TableQuery {

    /**
     * 模糊名称
     */
    private String nameLike;

}
