package zhangyu.fool.generate.random;

import java.util.Random;

/**
 * @author xiaomingzhang
 * @date 2021/8/20
 */
public abstract class AbstractRandom implements FoolRandom {

    protected static final Random random = new Random();

    protected int getRandomInt(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

}
