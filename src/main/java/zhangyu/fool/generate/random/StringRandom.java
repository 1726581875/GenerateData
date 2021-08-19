package zhangyu.fool.generate.random;

import zhangyu.fool.generate.annotation.BindType;
import zhangyu.fool.generate.annotation.Char;
import zhangyu.fool.generate.annotation.Number;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType(String.class)
public class StringRandom implements FoolRandom {

    protected static final Random random = new Random();

    @Override
    public String randomValue(Field field) {
        if(field == null || field.getAnnotation(Char.class) == null) {
            return randomStr(10);
        }
        Char aChar = field.getAnnotation(Char.class);
        //配置了固定值则直接返回对应固定值
        if(!"".equals(aChar.fixed())) {
            return aChar.fixed();
        }
        int length = random.nextInt(aChar.max() + aChar.min()) - aChar.min();
        return randomStr(length);

    }

    @Override
    public Object randomValue() {
        return this.randomValue(null);
    }

    private String randomStr(int length) {
        char[] chars = new char[length];
        for(int i = 0; i < length; i++) {
            //小写字母ASCII范围97~122
            int num = random.nextInt(122 - 97 + 1) + 97;
            chars[i] = (char)num;
        }
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        StringRandom stringRandom = new StringRandom();
        System.out.println(stringRandom.randomValue());
    }

}
