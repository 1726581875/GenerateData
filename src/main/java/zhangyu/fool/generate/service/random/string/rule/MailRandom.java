package zhangyu.fool.generate.service.random.string.rule;

import zhangyu.fool.generate.service.random.string.StringRandom;

/**
 * @author xiaomingzhang
 * @date 2021/9/3
 */
public class MailRandom extends StringRandom implements RuleStringRandom{


    @Override
    public String randomRuleString() {
        return "xmz_mail@163.com";
    }
}
