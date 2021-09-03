package zhangyu.fool.generate.service.random.string.rule;

import zhangyu.fool.generate.annotation.BindRole;
import zhangyu.fool.generate.enums.RuleType;
import zhangyu.fool.generate.service.random.string.StringRandom;

/**
 * @author xiaomingzhang
 * @date 2021/9/3
 */
@BindRole(RuleType.NAME)
public class PhoneNumberRandom extends StringRandom implements RuleStringRandom {

    @Override
    public String randomRuleString() {
        return FAKER.phoneNumber().cellPhone();
    }

    public static void main(String[] args) {
        PhoneNumberRandom phoneNumberRandom = new PhoneNumberRandom();
        System.out.println(phoneNumberRandom.randomRuleString());
    }
}
