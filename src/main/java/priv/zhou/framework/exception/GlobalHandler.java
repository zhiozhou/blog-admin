package priv.zhou.framework.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import priv.zhou.common.domain.vo.OutVO;
import priv.zhou.common.param.AppProperties;
import priv.zhou.common.param.NULL;
import priv.zhou.common.param.OutVOEnum;
import priv.zhou.common.tools.EmailUtil;
import priv.zhou.common.tools.HttpUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author zhou
 * @since 2020.03.04
 */
@Slf4j
@ControllerAdvice
public class GlobalHandler {

    private AppProperties appProperties;

    public GlobalHandler(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * 全局异常
     */
    @ExceptionHandler(value = Exception.class)
    public void globalHand(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        StringBuilder builder = new StringBuilder("未知异常: request -->").append(request.getRequestURI()).append(" | ");
        builder.append("请求参数 -->").append(HttpUtil.getParams(request)).append(" | ");
        builder.append("e -->");
        log.error(builder.toString(), e);
        HttpUtil.out(response, OutVO.fail(OutVOEnum.ERROR_SYSTEM));
        if(appProperties.isEmail()){
            EmailUtil.send(appProperties.getAdminEmail(), appProperties.getName() + " 出现未知异常", getStackTrace(e));
        }
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
    @ExceptionHandler(BindException.class)
    public void bindHand(HttpServletRequest request, HttpServletResponse response, BindException e) throws Exception {
        List<ObjectError> errs = e.getBindingResult().getAllErrors();
        OutVO<NULL> outVO = OutVO.fail(OutVOEnum.FAIL_PARAM).setInfo(errs.get(0).getDefaultMessage());
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), outVO);
        HttpUtil.out(response, outVO);
    }

    /**
     * 全局错误异常
     */
    @ExceptionHandler(GlobalException.class)
    public void globalFailHand(HttpServletRequest request, HttpServletResponse response, GlobalException e) throws Exception {
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), e.getOutVO());
        HttpUtil.out(response, e.getOutVO());
    }


    /**
     * 无权限异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public void globalFailHand(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutVO<NULL> outVO = OutVO.fail(OutVOEnum.ILLEGAL_VISIT);
        log.info("退出 {} 接口,返回报文 -->{}\n", request.getRequestURI(), outVO);
        HttpUtil.out(response, outVO);
    }


    private String getStackTrace(Throwable e) {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(outStream)) {
            e.printStackTrace(writer);
            writer.flush();
            return outStream.toString(AppProperties.ENC);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
