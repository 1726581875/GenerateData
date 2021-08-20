package zhangyu.fool.generate.random.string;

import zhangyu.fool.generate.annotation.BindType;
import zhangyu.fool.generate.annotation.feild.Char;
import zhangyu.fool.generate.enums.RuleType;
import zhangyu.fool.generate.random.AbstractRandom;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
@BindType(String.class)
public class StringRandom extends AbstractRandom {

    protected static final Random random = new Random();

    @Override
    public String randomValue(Field field) {
        if(field == null || field.getAnnotation(Char.class) == null) {
            return randomStr(10);
        }
        Char annotation = field.getAnnotation(Char.class);
        //如果配置了固定值则直接返回对应固定值
        if(!"".equals(annotation.fixed())) {
            return getFixedValueRandom(annotation.fixed());
        }
        //如果有规则，则按照规则生成
        if(!RuleType.NON.equals(annotation.rule())) {
           return getRuleRandomString(annotation.rule());
        }

        //否则随机生成一串小写字母
        int length = getRandomInt(annotation.min(),annotation.max());
        return randomStr(length);

    }

    private String getRuleRandomString(RuleType ruleType) {
        String ruleStr = "";
        try {
            RuleStringRandom ruleStringRandom = (RuleStringRandom)ruleType.getClazz().newInstance();
            ruleStr = ruleStringRandom.randomRuleString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ruleStr;
    }



    @Override
    public Object randomValue() {
        return this.randomValue(null);
    }

    private String getFixedValueRandom(String fixedRule){
        String[] split = fixedRule.split("\\|\\|");
        int index = getRandomInt(0, split.length - 1);
        return split[index].trim();
    }



    private String randomStr(int length) {
        char[] chars = new char[length];
        for(int i = 0; i < length; i++) {
            //小写字母ASCII范围97~122
            int num = getRandomInt(97, 122);
            chars[i] = (char)num;
        }
        return String.valueOf(chars);
    }


    public static void main(String[] args) {
        StringRandom stringRandom = new StringRandom();
        System.out.println(stringRandom.randomValue());
    }

}
