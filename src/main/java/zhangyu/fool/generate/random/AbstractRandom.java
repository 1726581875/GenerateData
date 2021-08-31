package zhangyu.fool.generate.random;

import com.github.javafaker.Faker;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Random;

/**
 * @author xiaomingzhang
 * @date 2021/8/20
 */
public abstract class AbstractRandom implements FoolRandom {

    protected static final Random random = new Random();

    protected Faker FAKER = new Faker(Locale.CHINA);

    protected int getRandomInt(int min, int max){

        return random.nextInt(max - min + 1) + min;
    }

    @Override
    public String randomValueAndToString(Field field) {
        return String.valueOf(this.randomValue(field));
    }

    protected boolean isExistAnnotation(Field field, Class<? extends Annotation> annotation){
        return (field != null && field.getAnnotation(annotation) != null);
    }

}
