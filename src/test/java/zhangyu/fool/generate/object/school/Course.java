package zhangyu.fool.generate.object.school;

import zhangyu.fool.generate.annotation.feild.Char;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Ignore;
import zhangyu.fool.generate.annotation.feild.Number;
import zhangyu.fool.generate.enums.IdType;
import zhangyu.fool.generate.enums.RuleType;

import java.util.Date;

/**
 * @author xmz
 * @date: 2021/08/31
 */
public class Course{
    /**
     * 主键id
     */
    @Id(IdType.AUTH)
    private Long id;
    /**
     * 课程名
     */
    @Char(rule = RuleType.NAME)
    private String name;
    /**
     * 教师id
     */
    private Long teacherId;
    /**
     * 状态|1正常、2已删除
     */
    @Number(min = 1, max = 1)
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

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherId=" + teacherId +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}