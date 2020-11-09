package priv.zhou.framework.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import priv.zhou.common.resolver.CipherIdResolver;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

/**
 * 应用全局配置
 *
 * @author zhou
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    RequestMappingHandlerAdapter adapter;

    @PostConstruct
    public void injectSelfMethodArgumentResolver() {
        List<HandlerMethodArgumentResolver> argumentResolvers = Lists.newArrayList(Objects.requireNonNull(adapter.getArgumentResolvers()));
        argumentResolvers.add(3, new CipherIdResolver());
        adapter.setArgumentResolvers(argumentResolvers);
    }


}
