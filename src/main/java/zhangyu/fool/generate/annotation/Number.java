package zhangyu.fool.generate.annotation;

import zhangyu.fool.generate.enums.RuleTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiamingzhang
 * @data 2021/08/19
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Number {

    String value() default "";

    int min() default 0;

    int max() default 2147483647;
}
