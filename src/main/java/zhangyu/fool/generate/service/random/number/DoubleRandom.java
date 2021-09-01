package zhangyu.fool.generate.service.random.number;

import zhangyu.fool.generate.annotation.BindType;

import java.lang.reflect.Field;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType(Double.class)
public class DoubleRandom extends NumberRandom {
    @Override
    public Object randomValue(Field field) {
        return random.nextDouble();
    }
}
