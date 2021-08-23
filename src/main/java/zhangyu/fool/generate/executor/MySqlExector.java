package zhangyu.fool.generate.executor;

import zhangyu.fool.generate.util.ConnectUtil;

import java.sql.Connection;
import java.sql.Statement;
/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MySqlExector implements SqlExecutor {

    @Override
    public void execute(String sql) {
        try (Connection connection = ConnectUtil.getConnection();
             Statement statement = connection.createStatement()) {
                statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("execute sql execute", e);
        }
    }
}
