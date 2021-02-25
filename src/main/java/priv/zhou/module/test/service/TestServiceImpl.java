package priv.zhou.module.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import priv.zhou.common.constant.NULL;
import priv.zhou.common.constant.Save;
import priv.zhou.common.domain.Result;
import priv.zhou.common.service.BaseService;
import priv.zhou.module.test.domain.dto.TestDTO;

import javax.validation.constraints.NotNull;


/**
 * 博客 服务层实现
 *
 * @author zhou
 * @since 2020.09.14
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl extends BaseService implements ITestService {

    @Override
    public Result<NULL> save(TestDTO testDTO) {
        return Result.success();
    }
}
