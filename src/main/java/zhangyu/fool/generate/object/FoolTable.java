package zhangyu.fool.generate.object;
import zhangyu.fool.generate.annotation.feild.Char;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.annotation.feild.Number;
import zhangyu.fool.generate.enums.IdType;
import zhangyu.fool.generate.enums.RuleType;

/**
 * @author xmz
 * @date: 2021/08/24
 */
public class FoolTable{
    /**
     * ??id
     */
    @Id(IdType.AUTH)
    private Long id;
    /**
     * 名字
     */
    @Char(rule = RuleType.NAME)
    private String name;

    @Join(object = FoolDatabase.class, field = "id", rel = 2)
    private Long databaseId;
    /**
     * 表描述
     */
    @Char(rule = RuleType.SCHOOL)
    private String tableComment;

    @Number(min = 0,max = 1)
    private Integer status;
}