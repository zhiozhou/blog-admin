package priv.zhou.common.domain.vo;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 列表分页 通用返回模型
 *
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class TableVO<VO> {

    private List<VO> list;

    private Long count;

    private TableVO(List<VO> list) {
        this.list = list;
        this.count = new PageInfo<>(list).getTotal();
    }

    public static <T> TableVO<T> build(List<T> list) {
        return new TableVO<>(list);
    }

}
