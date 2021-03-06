package zhangyu.fool.generate.service.random;

import java.lang.reflect.Field;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public interface FoolRandom {

    /**
     * 根据类型生产一个随机值
     * @param field java类型
     * @return 随机值
     */
    Object randomValue(Field field);

    /**
     * 产生一个随机值
     * @return
     */
    Object randomValue();

    /**
     * 获取转换String类型的随机值
     * @param field
     * @return
     */
    String randomValueAndToString(Field field);
}
