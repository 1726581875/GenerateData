package zhangyu.fool.generate.service.random;

import zhangyu.fool.generate.annotation.BindType;

import java.lang.reflect.Field;

/**
 * @author xmz
 * @date: 2021/08/21
 */
@BindType({Boolean.class, boolean.class})
public class BooleanRandom extends AbstractRandom {
    @Override
    public Boolean randomValue(Field field) {
        return randomValue();
    }

    @Override
    public Boolean randomValue() {
        return random.nextBoolean();
    }
}
