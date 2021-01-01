package priv.zhou.common.service;

import com.github.pagehelper.PageHelper;
import priv.zhou.common.domain.dto.Page;

public class BaseService {

    protected void startPage(Page page) {
        if (page != null) {
            PageHelper.startPage(page.getPage(), page.getLimit());
        }
    }
}
