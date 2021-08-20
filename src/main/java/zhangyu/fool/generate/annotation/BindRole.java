package zhangyu.fool.generate.annotation;

import zhangyu.fool.generate.enums.RuleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiamingzhang
 * @data 2021/08/20
 * 标注random类对应的规则
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindRole {
    RuleType value();
}
