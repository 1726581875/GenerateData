package zhangyu.fool.generate.object.test.dm;

/**
 * @author xiaomingzhang
 * @date 2021/12/16
 */
public class Area {

    private int id;

    private int areaID;

    private String area;

    private String fatherID;

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", areaID=" + areaID +
                ", area='" + area + '\'' +
                ", fatherID='" + fatherID + '\'' +
                '}';
    }
}
