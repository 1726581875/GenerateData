package zhangyu.fool.generate.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

}
