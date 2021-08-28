package zhangyu.fool.generate.annotation.feild;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiaomingzhang
 * @date 2021/08/27
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    /**
     * 关联对象
     * @return
     */
    Class<?> object();

    /**
     * 关联列名
     * @return
     */
    String field();

    /**
     * 关联关系
     * 1:一对一
     * 2：一对二
     * 3：一对三
     * ...
     * @return
     */
    int rel() default 1;
}
