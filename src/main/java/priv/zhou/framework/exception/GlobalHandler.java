package priv.zhou.framework.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.zhou.common.interfaces.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.properties.AppProperties;
import priv.zhou.common.tools.DateUtil;
import priv.zhou.common.tools.EmailUtil;
import priv.zhou.common.tools.HttpUtil;
import priv.zhou.common.tools.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static priv.zhou.common.constant.GlobalConst.DEFAULT_ENC;
import static priv.zhou.common.constant.GlobalConst.STR_0;
import static priv.zhou.common.constant.RedisConst.EXCEPTION_COUNT_KEY;
import static priv.zhou.common.constant.RedisConst.EXCEPTION_SENT_KEY;

/**
 * 全局异常捕获
 *
 * @author zhou
 * @since 2020.03.04
 */
@Slf4j
@Controller
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalHandler {

    private final AppProperties appProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 全局异常
     * 10分钟内相同异常只发送一次，尾部附带当日异常统计
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> unexpectedHandle(HttpServletRequest request, Exception e) throws JsonProcessingException {
        StringBuilder infoBuilder = new StringBuilder("未知异常: request -->").append(request.getRequestURI()).append(" | ");
        infoBuilder.append("请求参数 -->").append(objectMapper.writeValueAsString(HttpUtil.getParams(request)));
        String info = infoBuilder.toString(), name = e.getClass().getName();
        log.error(info, e);
        RedisUtil.Map.incr(EXCEPTION_COUNT_KEY, name, 1L, DateUtil.restOfToday());
        if (RedisUtil.setIfAbsent(EXCEPTION_SENT_KEY + name, STR_0, 10, TimeUnit.MINUTES)) {
            StringBuilder countBuilder = new StringBuilder(getStackTrace(e))
                    .append("\n")
                    .append("----------------------------------- 今日未知异常统计 -----------------------------------")
                    .append("\n");
            Map<Object, Object> entries = RedisUtil.Map.entries(EXCEPTION_COUNT_KEY);
            for (Map.Entry<Object, Object> entry : entries.entrySet()) {
                countBuilder.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
            }
            EmailUtil.send(appProperties.getName() + " thrown " + name, info + "\n" + countBuilder, appProperties.getAdminEmail());
        }
        return Result.fail(ResultEnum.ERROR_SYSTEM);
    }

    /**
     * 全局错误异常
     */
    @ResponseBody
    @ExceptionHandler(RestException.class)
    public Result<?> restHandle(HttpServletRequest request, RestException e) {
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), e.getResult());
        return e.getResult();
    }

    /**
     * 强提醒异常捕获
     * 发送邮件
     */
    @ResponseBody
    @ExceptionHandler(RemindException.class)
    public Result<?> remindHandle(HttpServletRequest request, RemindException e) {
        EmailUtil.send(appProperties.getName() + " 强提醒异常!!!", e.getRemind(), appProperties.getAdminEmail());
        return restHandle(request, e);
    }

    /**
     * 请求方式不支持
     */
    @ResponseBody
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<NULL> notSupportedHandle(HttpRequestMethodNotSupportedException e) {
        return Result.render(ResultEnum.NOT_SUPPORTED_REQUEST, e.getMethod());
    }

    /**
     * 验证异常
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result<?> bindHandle(HttpServletRequest request, BindException e) {
        List<ObjectError> errs = e.getBindingResult().getAllErrors();
        Result<?> result = Result.fail(ResultEnum.FAIL_PARAM).setInfo(errs.get(0).getDefaultMessage());
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), result);
        return result;
    }

    /**
     * 无权限异常
     */
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result<?> UnauthorizedHandle(HttpServletRequest request) {
        Result<NULL> result = Result.fail(ResultEnum.ILLEGAL_VISIT);
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), result);
        return result;
    }


    public static String getStackTrace(Throwable e) {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(outStream)) {
            e.printStackTrace(writer);
            writer.flush();
            return outStream.toString(DEFAULT_ENC);
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
