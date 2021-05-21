package priv.zhou.module.test.service;

import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.module.test.domain.dto.TestDTO;

/**
 * 测试 服务层定义
 *
 * @author zhou
 * @since 2020.09.14
 */
public interface ITestService {

    Result<NULL> save(TestDTO testDTO);

}
