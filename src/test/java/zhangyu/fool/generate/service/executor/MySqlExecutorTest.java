package zhangyu.fool.generate.service.executor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.service.BaseTest;
import zhangyu.fool.generate.service.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.service.builder.SqlBuilder;
import zhangyu.fool.generate.service.runner.MySqlRunner;
import zhangyu.fool.generate.util.NameUtil;

import java.util.List;

/**
 * @author xmz
 * @date: 2021/08/28
 */
public class MySqlExecutorTest extends BaseTest {

    private SqlBuilder sqlBuilder;

    private SqlExecutor sqlExecutor;

    private MySqlRunner mySqlRunner;

    @BeforeEach
    void init() {
        sqlBuilder = new MySqlSqlBuilder();
        sqlExecutor = new MySqlExecutor();
        mySqlRunner = new MySqlRunner();
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
            Object result = sqlExecutor.getOne(sql, Long.class);
            Long maxId = result == null ? 0L : (Long) result;
            Assertions.assertTrue(maxId >= 0L && maxId <= Long.MAX_VALUE);
            System.out.println("maxId=" + maxId);
        });
    }

    @Test
    @DisplayName("获取单个结果 getOne 函数测试")
    public void getOneTest() {
        foreachTest(clazz -> {
            int row = getRandomInt(1, 20);
            // 初始化插入数据
            mySqlRunner.run(clazz, row);
            String querySql = "select * from " + NameUtil.convertToDataBaseRule(clazz.getSimpleName()) + " limit 1";
            Object result = sqlExecutor.getOne(querySql, clazz);
            System.out.println(result);
        });
    }


    @Test
    @DisplayName("获取多个结果 getList 函数测试")
    public void getListTest() {
        foreachTest(clazz -> {
            int row = getRandomInt(1, 20);
            // 初始化插入数据
            mySqlRunner.run(clazz, row);
            String querySql = "select * from " + NameUtil.convertToDataBaseRule(clazz.getSimpleName()) + " limit " + row;
            List<?> list = sqlExecutor.getList(querySql, clazz);
            list.forEach(System.out::println);
            Assertions.assertEquals(row, list.size(), "查询数据量必须正确");
        });

    }

}
