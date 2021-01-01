package priv.zhou.common.domain.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 列表分页 通用返回模型
 * @author zhou
 */
@Getter
@Setter
@Accessors(chain = true)
public class TableVO<DTO> {

	private List<DTO> list;

	private Long count;
}
