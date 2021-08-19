package zhangyu.fool.generate;

import zhangyu.fool.generate.annotation.Char;
import zhangyu.fool.generate.annotation.Number;

import java.util.Date;

/**
 * @author xmz
 * @date: 2021/07/17
 */
public class FoolDatabase {
    /**
     * 主键id
     */
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
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}