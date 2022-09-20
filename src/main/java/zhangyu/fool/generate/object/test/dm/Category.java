package zhangyu.fool.generate.object.test.dm;

import java.util.Date;

public class Category {
    // id
    private Long id;
    // 分类名称   
    private String name;
    // 分类描述   
    private String description;
    // 创建时间
    private Date createTime;
    // 修改时间
    private Date updateTime;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}