package zhangyu.fool.generate.enums;

import zhangyu.fool.generate.service.random.string.rule.NameRandom;
import zhangyu.fool.generate.service.random.string.rule.PhoneNumberRandom;
import zhangyu.fool.generate.service.random.string.rule.RuleStringRandom;
import zhangyu.fool.generate.service.random.string.rule.SchoolRandom;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 * 规则类型枚举
 */
public enum RuleType {
    /**
     * 无规则
     */
    NON(RuleStringRandom.class),
    /**
     * 姓名
     */
    NAME(NameRandom.class),
    /**
     * 学校
     */
    SCHOOL(SchoolRandom.class),
    /**
     * 手机号码
     */
    PHONE(PhoneNumberRandom.class),
    ;
    private Class<?> clazz;

    RuleType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
