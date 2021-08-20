package zhangyu.fool.generate.object;

import zhangyu.fool.generate.annotation.feild.Char;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Ignore;
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
     * 主键id
     */
    @Id(IdType.AUTH)
    private Integer id;
    /**
     * 数据库名
     */
    @Char(rule = RuleType.NAME)
    private String name;
    /**
     * 所属数据源id
     */
    private Integer sourceId;
    /**
     * 状态|1正常、2已删除
     */
    @Number(min = 0,max = 1)
    private Integer status;
    /**
     * 创建时间
     */
    @Ignore
    private Date createTime;
    /**
     * 修改时间
     */
    @Ignore
    private Date updateTime;

}