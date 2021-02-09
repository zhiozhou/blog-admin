package priv.zhou.common.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @author LY
 */
@Component
@SuppressWarnings("unused")
public final class SpringUtils implements ApplicationContextAware {

    public static Boolean devEnc;

    public static ApplicationContext context;

    public static final String devEncProfile = "dev";

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtils.context = context;
    }

    /**
     * 获取环境前缀
     */
    public static String getProfile() {
        String[] profiles = context.getEnvironment().getActiveProfiles();
        if (0 == profiles.length) {
            profiles = context.getEnvironment().getDefaultProfiles();
        }
        return profiles[0];
    }

    /**
     * 获取对象
     */
    public static Object getBean(String name) throws BeansException {
        return context.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     */
    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return context.getBean(requiredType);
    }

    /**
     * 如果BeanFactory是否包含一个与所给名称匹配的bean定义
     */
    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     */
    public static boolean isSingleton(String beanName) throws NoSuchBeanDefinitionException {
        return context.isSingleton(beanName);
    }

    /**
     * 根据beanName获取类型
     */
    public static Class<?> getType(String beanName) throws NoSuchBeanDefinitionException {
        return context.getType(beanName);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return context.getAliases(name);
    }

    public static String getMessage(String key, Object... args) {
        return context.getMessage(key, args, Locale.getDefault());
    }


    /**
     * 是否为开发环境
     */
    public static boolean isDevEnc() {
        return null == devEnc ? devEnc = devEncProfile.equals(getProfile()) : devEnc;
    }

}
