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
}
