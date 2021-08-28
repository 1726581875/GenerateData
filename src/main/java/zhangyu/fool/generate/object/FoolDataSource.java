package zhangyu.fool.generate.object;
import zhangyu.fool.generate.annotation.TableName;
import zhangyu.fool.generate.annotation.feild.Id;
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
    private Integer status;
    /**
     * ????
     */
    private Date createTime;
    /**
     * ????
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", userId=").append(userId);
        sb.append(", hostName=").append(hostName);
        sb.append(", port=").append(port);
        sb.append(", sourceUrl=").append(sourceUrl);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}