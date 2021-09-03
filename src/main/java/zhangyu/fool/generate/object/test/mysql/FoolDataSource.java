package zhangyu.fool.generate.object.test.mysql;
import zhangyu.fool.generate.annotation.TableName;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Number;
import zhangyu.fool.generate.enums.IdType;

import java.util.Date;

/**
 * @author xmz
 * @date: 2021/08/24
 */
@TableName("fool_data_source")
public class FoolDataSource{

    @Id(IdType.AUTH)
    private Long id;
    /**
     * ?????
     */
    private String name;
    /**
     * ????id
     */
    private Long userId;
    /**
     * ?????ip??
     */
    private String hostName;
    /**
     * ??
     */
    private Integer port;
    /**
     * ???url
     */
    private String sourceUrl;
    /**
     * ????????
     */
    private String userName;
    /**
     * ???????
     */
    private String password;
    /**
     * ??|1???2???
     */
    @Number(min = 1,max = 1)
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