package zhangyu.fool.generate.service.random.number;

import zhangyu.fool.generate.annotation.BindType;
import java.lang.reflect.Field;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType({Long.class,long.class})
public class LongRandom extends NumberRandom {
    @Override
    public Long randomValue(Field field) {
        return random.nextLong();
    }
}
