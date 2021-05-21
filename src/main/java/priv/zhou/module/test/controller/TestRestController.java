package priv.zhou.module.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.interfaces.Save;
import priv.zhou.common.interfaces.Update;
import priv.zhou.common.domain.Result;
import priv.zhou.module.test.domain.dto.TestDTO;
import priv.zhou.module.test.service.ITestService;

/**
 * 测试 控制层
 *
 * @author zhou
 * @since 2020.04.17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/test/rest")
public class TestRestController {

    private final ITestService testService;

    @PostMapping("/save")
    public Result<NULL> save(@Validated({Save.class}) TestDTO testDTO) {
        if (StringUtils.isEmpty(testDTO.getCompanyId())) {

        }
        return Result.success();
    }


    @PostMapping("/update")
    public Result<NULL> update(@Validated({Update.class}) TestDTO testDTO) {
        if (StringUtils.isEmpty(testDTO.getId())) {

        }
        return Result.success();
    }

}
