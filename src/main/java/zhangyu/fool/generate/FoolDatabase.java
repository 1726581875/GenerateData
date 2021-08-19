package zhangyu.fool.generate;

import zhangyu.fool.generate.annotation.feild.Char;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Ignore;
import zhangyu.fool.generate.enums.IdType;

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
    @Char(fixed = "哈哈哈哈")
    private String name;
    /**
     * 所属数据源id
     */
    private Integer sourceId;
    /**
     * 状态|1正常、2已删除
     */
    private Integer status;
    /**
     * 创建时间
     */
    @Ignore
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}