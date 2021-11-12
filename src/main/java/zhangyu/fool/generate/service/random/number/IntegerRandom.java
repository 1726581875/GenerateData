package zhangyu.fool.generate.service.random.number;

import zhangyu.fool.generate.annotation.BindType;
import zhangyu.fool.generate.annotation.feild.Number;
import java.lang.reflect.Field;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType({Integer.class, int.class})
public class IntegerRandom extends NumberRandom {

    @Override
    public Integer randomValue(Field field) {
        if(isExistAnnotation(field,Number.class)) {
            Number number = field.getAnnotation(Number.class);
            return getRandomInt(number.min(), number.max());
        }
        return random.nextInt(Integer.MAX_VALUE);
    }

}
