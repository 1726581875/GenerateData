package zhangyu.fool.generate.object.test.mysql;

import zhangyu.fool.generate.annotation.feild.Char;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Number;
import zhangyu.fool.generate.enums.IdType;
import zhangyu.fool.generate.enums.RuleType;

import java.util.Date;

/**
 * @author xiaomingzhang
 * @date 2022/9/20
 */
public class UserTest {

    @Id(IdType.AUTH)
    private Long id;

    @Char(rule = RuleType.NAME)
    private String name;

    @Char(rule = RuleType.PHONE)
    private String phoneNum;

    @Char(rule = RuleType.SCHOOL)
    private String schoolName;

    @Number(min = 0,max = 1)
    private Integer status;

    private Date createTime;

}
