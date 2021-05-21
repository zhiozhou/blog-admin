package priv.zhou.common.domain.vo;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 表格渲染模型
 *
 * @author zhou
 * @since 0.1.0
 */
@Getter
public class TableVO<VO> {

    private final List<VO> list;

    private final Long count;

    private TableVO(List<VO> list) {
        this.list = list;
        this.count = new PageInfo<>(list).getTotal();
    }

    public static <T> TableVO<T> build(List<T> list) {
        return new TableVO<>(list);
    }

}
