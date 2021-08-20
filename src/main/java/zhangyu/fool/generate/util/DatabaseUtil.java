package zhangyu.fool.generate.util;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * @author xmz
 * 2020年9月12日
 * 数据库连接工具
 */
public class DatabaseUtil {
    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        // 获取到xml文件里配置的连接参数
        String url = "jdbc:mysql://localhost:3306/fool?characterEncoding=UTF-8&serverTimezone=GMT%2B8";
        String user = "root";
        String password = "root";
        String driver = "com.mysql.cj.jdbc.Driver";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Class.forName(driver);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据库连接失败");
        }
    }


}
