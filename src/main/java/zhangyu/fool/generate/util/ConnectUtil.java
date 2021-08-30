package zhangyu.fool.generate.util;

import zhangyu.fool.generate.reader.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * @author xmz
 * 2020年9月12日
 * 数据库连接工具
 */
public class ConnectUtil {
    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        // 获取到xml文件里配置的连接参数
        String url = PropertiesReader.get("url");
        String username = PropertiesReader.get("username");
        String password = PropertiesReader.get("password");
        String driver = PropertiesReader.get("driver");
        return getConnection(new Config(url, username, password, driver));
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

    final static class Config {
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
