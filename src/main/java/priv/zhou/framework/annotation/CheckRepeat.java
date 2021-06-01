package priv.zhou.framework.annotation;

import priv.zhou.framework.aspect.WebAspect;

import java.lang.annotation.*;

/**
 * 检查是否重复提交
 *
 * @author zhou
 * @see WebAspect#checkRepeat()
 * @since 2019.11.28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckRepeat {
}
