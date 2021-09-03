package zhangyu.fool.generate.object.test.mysql;

import zhangyu.fool.generate.annotation.feild.*;
import zhangyu.fool.generate.annotation.feild.Number;
import zhangyu.fool.generate.enums.IdType;
import zhangyu.fool.generate.enums.RuleType;

import java.util.Date;

/**
 * @author xmz
 * @date: 2021/07/17
 */
public class FoolDatabase {

    /**
     * id自增
     */
    @Id(IdType.AUTH)
    private Long id;

    /**
     * 字符串，规则为名字
     */
    @Char(rule = RuleType.NAME)
    private String name;

    /**
     * 不写注解，默认随机生成long数字
     */
    private Long sourceId;
    /**
     * 数字，范围是[0,1]
     */
    @Number(min = 0,max = 1)
    private Integer status;

    /**
     * 忽略的列，表示不需要生成
     */
    @Ignore
    private Date createTime;

    @Ignore
    private Date updateTime;

}