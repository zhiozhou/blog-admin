package priv.zhou.common.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用于list接口返回
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class ListVO<T> {

	private List<T> list;

	private Long count;
}
