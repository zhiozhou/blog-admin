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
import priv.zhou.common.constant.NULL;
import priv.zhou.common.domain.Result;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.properties.AppProperties;
import priv.zhou.common.tools.EmailUtil;
import priv.zhou.common.tools.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static priv.zhou.common.constant.GlobalConst.DEFAULT_ENC;

/**
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
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> globalHand(HttpServletRequest request, Exception e) throws JsonProcessingException {
        StringBuilder builder = new StringBuilder("未知异常: request -->").append(request.getRequestURI()).append(" | ");
        builder.append("请求参数 -->").append(objectMapper.writeValueAsString(HttpUtil.getParams(request))).append(" | ");
        builder.append("e -->");
        log.error(builder.toString(), e);
        EmailUtil.send(appProperties.getName() + " 出现未知异常", getStackTrace(e), appProperties.getAdminEmail());
        return Result.fail(ResultEnum.ERROR_SYSTEM);

    }


    /**
     * 不支持此请求方法异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public void NotSupportedHand() {
        log.info("Request method not supported");
    }


    /**
     * 验证异常
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result<?> bindHand(HttpServletRequest request, BindException e) {
        List<ObjectError> errs = e.getBindingResult().getAllErrors();
        Result<?> result = Result.fail(ResultEnum.FAIL_PARAM).setInfo(errs.get(0).getDefaultMessage());
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), result);
        return result;
    }

    /**
     * 全局错误异常
     */
    @ResponseBody
    @ExceptionHandler(RestException.class)
    public Result<?> globalFailHand(HttpServletRequest request, RestException e) {
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), e.getResult());
        return e.getResult();
    }


    /**
     * 无权限异常
     */
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public Result<?> globalFailHand(HttpServletRequest request) {
        Result<NULL> result = Result.fail(ResultEnum.ILLEGAL_VISIT);
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), result);
        return result;
    }


    private String getStackTrace(Throwable e) {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(outStream)) {
            e.printStackTrace(writer);
            writer.flush();
            return outStream.toString(DEFAULT_ENC);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
