package priv.zhou.framework.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.zhou.common.enums.ResultEnum;
import priv.zhou.common.tools.HttpUtil;
import priv.zhou.common.tools.Md5Util;
import priv.zhou.common.tools.RedisUtil;
import priv.zhou.common.tools.ShiroUtil;
import priv.zhou.framework.exception.RestException;
import priv.zhou.framework.shiro.session.ShiroSessionDAO;

import javax.servlet.http.HttpServletRequest;

import static priv.zhou.common.constant.GlobalConst.STR_0;
import static priv.zhou.common.constant.RedisConst.REDIS_CHECK_REPEAT_KEY;

/**
 * 校验&日志aop
 *
 * @author zhou
 * @since 2019.11.14
 */
@Slf4j
@Aspect
@Component
public class WebAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(public * priv.zhou.module.*.controller.*.*(..)) || execution(public * priv.zhou.module.*.*.controller.*.*(..))")
    public void webCut() {
    }

    /**
     * 重复提交检查
     */
    @Order(0)
    @Before(value = "@annotation(priv.zhou.framework.annotation.CheckRepeat)")
    public void checkRepeat() throws Exception {
        HttpServletRequest request = getRequest();
        String md5 = Md5Util.encrypt(JSON.toJSONString(request.getParameterMap(), SerializerFeature.MapSortField));
        if (!RedisUtil.setIfAbsent(REDIS_CHECK_REPEAT_KEY + request.getRequestURI() + md5, STR_0, 5)) {
            throw new RestException(ResultEnum.OFTEN_OPERATION);
        }
    }

    /**
     * 请求日志
     */
    @Order(0)
    @Before(value = "webCut()")
    public void beforeLog() throws JsonProcessingException {
        HttpServletRequest request = getRequest();
        log.info("调用 {} 接口,请求参数 -->{}", request.getRequestURI(), objectMapper.writeValueAsString(HttpUtil.getParams(request)));
    }


    /**
     * 返回日志
     */
    @AfterReturning(returning = "result", pointcut = "webCut()")
    public void AfterExec(Object result) throws JsonProcessingException {
        log.info("退出 {} 接口,返回报文 -->{}\n", getRequest().getRequestURI(), objectMapper.writeValueAsString(result));
    }


    /**
     * 获取请求
     */
    private HttpServletRequest getRequest() {
        // 1.获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            throw new RestException(ResultEnum.ERROR_SYSTEM);
        }
        return attributes.getRequest();
    }

}
