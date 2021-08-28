package zhangyu.fool.generate.runner;

/**
 * @author xmz
 * @date: 2021/08/28
 */
public class JoinTreeNode {

    private Class<?> objectClass;

    private String joinField;

    private int rel;

    private int row;

    private JoinTreeNode parentNode;

    public JoinTreeNode(Class<?> objectClass, String joinField, int rel, int row, JoinTreeNode parentNode) {
        this.objectClass = objectClass;
        this.joinField = joinField;
        this.rel = rel;
        this.row = row;
        this.parentNode = parentNode;
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

    public JoinTreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(JoinTreeNode parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public String toString() {
        return "JoinTreeNode{" +
                "object=" + objectClass +
                ", joinField='" + joinField + '\'' +
                ", rel=" + rel +
                ", row=" + row +
                ", parentNode=" + parentNode +
                '}';
    }
}
