package zhangyu.fool.generate.object;

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

    @Id(IdType.AUTH)
    private Long id;
    @Char(rule = RuleType.NAME)
    private String name;

    private Long sourceId;
    /**
     * 状态|1正常、2已删除
     */
    @Number(min = 0,max = 1)
    private Integer status;
    @Ignore
    private Date createTime;
    @Ignore
    private Date updateTime;

}