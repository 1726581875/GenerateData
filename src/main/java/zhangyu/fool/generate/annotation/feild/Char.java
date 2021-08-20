package zhangyu.fool.generate.annotation.feild;

import zhangyu.fool.generate.enums.RuleType;

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
public @interface Char {

    String value() default "";

    /**
     * 字符长度
     * @return
     */
    int min() default 0;

    int max() default 10;

    RuleType rule() default RuleType.NON;

    /**
     * 配置固定值
     * @return
     */
    String fixed() default "";
}
