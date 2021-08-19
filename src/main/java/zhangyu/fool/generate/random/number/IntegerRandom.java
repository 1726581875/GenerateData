package zhangyu.fool.generate.random.number;

import zhangyu.fool.generate.annotation.BindType;
import zhangyu.fool.generate.annotation.feild.Number;
import java.lang.reflect.Field;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType(Integer.class)
public class IntegerRandom extends NumberRandom {

    @Override
    public Integer randomValue(Field field) {
        if(field != null) {
            Number number = field.getAnnotation(Number.class);
            if (number != null) {
                return random.nextInt(number.max() - number.min() + 1) + number.min();
            }
        }
        return random.nextInt(Integer.MAX_VALUE);
    }

}
