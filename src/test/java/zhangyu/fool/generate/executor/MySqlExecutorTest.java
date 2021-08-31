package zhangyu.fool.generate.executor;

import org.h2.util.ScriptReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import zhangyu.fool.generate.BaseTest;
import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.builder.SqlBuilder;
import zhangyu.fool.generate.object.school.Course;

import java.sql.*;

/**
 * @author xmz
 * @date: 2021/08/28
 */
public class MySqlExecutorTest extends BaseTest {

    private SqlBuilder sqlBuilder;

    private SqlExecutor sqlExecutor;

    @BeforeEach
    void init() {
        sqlBuilder = new MySqlSqlBuilder();
        sqlExecutor = new MySqlExecutor();
    }

    @Test
    public void executeTest() {
        String sql = sqlBuilder.buildSelectMaxIdSql(Course.class, "id");
        System.out.println(sql);
        Object result = sqlExecutor.execute(sql, Long.class);
        Long maxId = result == null ? 0L : (Long) result;
        System.out.println(maxId);
    }

    @Test
    public void test() {
        try(Connection conn = DriverManager.getConnection("jdbc:h2:mem:fool", "sa", "");
            Statement statement = conn.createStatement()){
            statement.execute("create table `test` (`id` varchar (10), `name` varchar (10), primary key (`id`))");
            ResultSet resultSet = statement.executeQuery("select count (*) from test2");
            resultSet.next();
            System.out.println(resultSet.getString(1));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("结束");
    }

}
