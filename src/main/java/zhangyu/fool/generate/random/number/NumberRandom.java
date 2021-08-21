package zhangyu.fool.generate.random.number;

import zhangyu.fool.generate.random.AbstractRandom;
import zhangyu.fool.generate.random.FoolRandom;

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
