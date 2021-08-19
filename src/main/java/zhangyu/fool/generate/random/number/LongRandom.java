package zhangyu.fool.generate.random.number;

import zhangyu.fool.generate.annotation.BindType;
import zhangyu.fool.generate.annotation.Number;
import java.lang.reflect.Field;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType(Long.class)
public class LongRandom extends NumberRandom {
    @Override
    public Long randomValue(Field field) {
        if(field != null) {
            Number number = field.getAnnotation(Number.class);
            if (number != null) {
                //todo 更改为生成范围内的值
                return  random.nextLong();
            }
        }
        return random.nextLong();
    }
}
