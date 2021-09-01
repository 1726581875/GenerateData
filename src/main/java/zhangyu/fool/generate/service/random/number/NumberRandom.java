package zhangyu.fool.generate.service.random.number;

import zhangyu.fool.generate.service.random.AbstractRandom;

import java.util.Random;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public abstract class NumberRandom extends AbstractRandom {

    protected static final Random random = new Random();

    @Override
    public Object randomValue() {
        return this.randomValue(null);
    }

}
