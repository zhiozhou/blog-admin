package priv.zhou.common.service;

import com.github.pagehelper.PageHelper;
import priv.zhou.common.domain.dto.Page;

/**
 * 基础服务层
 *
 * @author zhou
 * @since 0.1.0
 */
public class BaseService {

    protected void startPage(Page page) {
        if (page != null) {
            PageHelper.startPage(page.getPage(), page.getLimit());
        }
    }
}
