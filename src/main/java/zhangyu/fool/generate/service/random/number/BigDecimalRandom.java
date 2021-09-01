package zhangyu.fool.generate.service.random.number;

import zhangyu.fool.generate.annotation.BindType;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * @author xmz
 * @date: 2021/08/21
 */
@BindType(BigDecimal.class)
public class BigDecimalRandom extends NumberRandom {

    @Override
    public Object randomValue(Field field) {
        float floatValue = random.nextFloat();
        return new BigDecimal(floatValue).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
