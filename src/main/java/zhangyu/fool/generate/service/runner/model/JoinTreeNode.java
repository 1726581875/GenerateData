package zhangyu.fool.generate.service.runner.model;

import java.util.List;

/**
 * @author xmz
 * @date: 2021/08/28
 */
public class JoinTreeNode {

    private Class<?> objectClass;
    /**
     * @see @Join 注解里的field属性列
     */
    private String joinField;
    /**
     * 绑定列，标记@Join注解所在列名
     */
    private String bindField;

    private int rel;

    private int row;

    private List<JoinTreeNode> childrenNode;

    public JoinTreeNode(Class<?> objectClass, String joinField, int rel, int row) {
        this.objectClass = objectClass;
        this.joinField = joinField;
        this.rel = rel;
        this.row = row;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    public String getJoinField() {
        return joinField;
    }

    public void setJoinField(String joinField) {
        this.joinField = joinField;
    }

    public int getRel() {
        return rel;
    }

    public void setRel(int rel) {
        this.rel = rel;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public List<JoinTreeNode> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<JoinTreeNode> childrenNode) {
        this.childrenNode = childrenNode;
    }

    public String getBindField() {
        return bindField;
    }

    public void setBindField(String bindField) {
        this.bindField = bindField;
    }

    @Override
    public String toString() {
        return "JoinTreeNode{" +
                "objectClass=" + objectClass +
                ", joinField='" + joinField + '\'' +
                ", bindField='" + bindField + '\'' +
                ", rel=" + rel +
                ", row=" + row +
                ", childrenNode=" + childrenNode +
                '}';
    }
}
