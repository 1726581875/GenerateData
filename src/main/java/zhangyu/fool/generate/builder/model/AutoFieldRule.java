package zhangyu.fool.generate.builder.model;

/**
 * @author xmz
 * @date: 2021/08/29
 */
public class AutoFieldRule {

    private String name;
    /**
     * 自增开始下标
     */
    private Long autoNum;
    /**
     * 允许自增范围限制，非id字段只能取(autoNum , autoNum + amount]围内的值
     */
    private Long limit;

    public AutoFieldRule(String name, Long autoNum, Long amount) {
        this.name = name;
        this.autoNum = autoNum;
        this.limit = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAutoNum() {
        return autoNum;
    }

    public void setAutoNum(Long autoNum) {
        this.autoNum = autoNum;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
