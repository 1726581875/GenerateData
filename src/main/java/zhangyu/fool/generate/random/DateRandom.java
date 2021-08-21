package zhangyu.fool.generate.random;

import zhangyu.fool.generate.annotation.BindType;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType(Date.class)
public class DateRandom extends AbstractRandom {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object randomValue(Field field) {
        return new Date();
    }

    @Override
    public Object randomValue() {
        return randomValue(null);
    }
}
