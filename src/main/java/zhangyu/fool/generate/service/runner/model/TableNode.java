package zhangyu.fool.generate.service.runner.model;

import java.util.List;

/**
 * @author xmz
 * @date: 2021/08/28
 */
public class TableNode {

    /**
     * @Join 注解绑定的class
     */
    private Class<?> objectClass;
    /**
     * @Join 注解里的field属性列值
     * 根节点则是null
     */
    private String joinField;
    /**
     * 绑定列，被标记@Join注解所在列名
     */
    private String bindField;
    /**
     * 关系
     */
    private int rel;
    /**
     *
     */
    private int row;

    private List<TableNode> childrenNode;

    public TableNode(Class<?> objectClass, String joinField, int rel, int row, String bindField) {
        this.objectClass = objectClass;
        this.joinField = joinField;
        this.rel = rel;
        this.row = row;
        this.bindField = bindField;
    }

    public boolean isRootNode() {
        return joinField == null;
    }

    public boolean notIsRootNode(){
        return !isRootNode();
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

    public List<TableNode> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<TableNode> childrenNode) {
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
