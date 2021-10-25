package zhangyu.fool.generate.object.school;

import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Ignore;
import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.annotation.feild.Number;
import zhangyu.fool.generate.enums.IdType;

import java.util.Date;

/**
 * @author xmz
 * @date: 2021/08/31
 */
public class Sc {
    /**
     * 主键id
     */
    @Id(IdType.AUTH)
    private Long id;
    /**
     * 教师id
     */
    @Join(object = Student.class, field = "id", rel = 1)
    private Long studentId;
    /**
     * 课程id
     */
    @Join(object = Course.class, field = "id", rel = 3)
    private Long courseId;
    /**
     * 分数
     */
    @Number(min = 0, max = 100)
    private Integer grade;
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
        return "Sc{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", grade=" + grade +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}