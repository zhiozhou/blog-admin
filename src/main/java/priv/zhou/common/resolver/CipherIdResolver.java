package priv.zhou.common.resolver;


import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMethodArgumentResolver;
import priv.zhou.common.annotation.CipherId;

import javax.servlet.http.HttpServletRequest;


/**
 * 解析用户
 *
 * @author zhou
 * @since 2019.5.16
 */
@Component
public class CipherIdResolver extends PathVariableMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CipherId.class);
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        Object o = super.resolveName(name, parameter, request);
        return o;
    }
}
