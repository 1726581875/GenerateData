package zhangyu.fool.generate.service.executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.service.BaseTest;
import zhangyu.fool.generate.service.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.service.builder.SqlBuilder;

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
    @DisplayName("执行 insert into 语句")
    public void executeInsertSqlTest(){
        foreachTest(clazz -> {
            // build sql
            String sql = sqlBuilder.buildInsertSql(clazz, getRandomInt(1, 20));
            Assertions.assertNotNull(sql, "must not be null");
            System.out.println("sql=" + sql);
            System.out.println("=====分隔线===");
            // exec sql
            sqlExecutor.execute(sql);
        });
    }


    @Test
    @DisplayName("执行 select max(id)语句")
    public void executeGetMaxIdTest() {
       foreachTest(clazz -> {
            // build sql
            String sql = sqlBuilder.buildSelectMaxIdSql(clazz, "id");
            Assertions.assertNotNull(sql, "must not be null");
            System.out.println("sql=" + sql);
            // exec sql
            Object result = sqlExecutor.execute(sql, Long.class);
            Long maxId = result == null ? 0L : (Long) result;
            Assertions.assertTrue(maxId >= 0L && maxId <= Long.MAX_VALUE);
            System.out.println("maxId=" + maxId);
        });
    }

}
