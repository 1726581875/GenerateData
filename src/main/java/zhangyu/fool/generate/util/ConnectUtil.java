package zhangyu.fool.generate.util;

import zhangyu.fool.generate.service.reader.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * @author xmz
 * 2020年9月12日
 * 数据库连接工具
 */
public class ConnectUtil {

    private static Config config;

    public static final String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String H2_DRIVER = "org.h2.Driver";

    static {
        // 获取到xml文件里配置的连接参数
        String url = PropertiesReader.get("mysql.url");
        String username = PropertiesReader.get("mysql.username");
        String password = PropertiesReader.get("mysql.password");
        String driver = PropertiesReader.get("mysql.driver");
        config = new Config(url, username, password, driver);
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        return getConnection(config);
    }


    public static Connection getConnection(Config config){
        try {
            Class.forName(config.getDriver());
            Connection conn = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
            return conn;
        } catch (Exception e) {
            throw new RuntimeException("获取数据库连接失败", e);
        }
    }

    public static void setConfig(Config connConfig){
        config = connConfig;
    }


    public final static class Config {
        String url;
        String username;
        String password;
        String driver;

        public Config(String url, String username, String password, String driver) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.driver = driver;
        }

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getDriver() {
            return driver;
        }
    }



}
