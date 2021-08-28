package zhangyu.fool.generate.object;

import zhangyu.fool.generate.annotation.TableName;
import java.util.Date;

/**
 * @author xmz
 * @date: 2021/08/24
 */
@TableName("fool_field")
public class FoolField{

    private Long id;
    /**
     * ???
     */
    private String name;
    /**
     * ???|pri???uk???
     */
    private String keyType;
    /**
     * ??????id
     */
    private Long tableId;
    /**
     * ????
     */
    private String fieldComment;
    /**
     * ????
     */
    private String fieldType;
    /**
     * ????
     */
    private Integer fieldLength;
    /**
     * ??|1???2???
     */
    private Integer status;
    /**
     * ????
     */
    private Date createTime;
    /**
     * ????
     */
    private Date updateTime;

}