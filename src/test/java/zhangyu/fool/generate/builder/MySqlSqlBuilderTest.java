package zhangyu.fool.generate.builder;

import org.junit.jupiter.api.*;
import zhangyu.fool.generate.BaseTest;
import zhangyu.fool.generate.object.FoolDatabase;
import zhangyu.fool.generate.object.school.Course;

import java.util.Random;

/**
 * @author xmz
 * @date: 2021/08/19
 */
@DisplayName("MySqlSqlBuilderTest")
public class MySqlSqlBuilderTest extends BaseTest {

    private SqlBuilder sqlSqlBuilder;

    @BeforeEach
    void init() {
        sqlSqlBuilder = new MySqlSqlBuilder();
    }


    @RepeatedTest(2)
    @DisplayName("构建insert语句")
    public void buildInsertSqlTest() {
        for (Class<?> clazz : entityArray) {
            MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
            String sql = mySqlSqlBuilder.buildInsertSql(clazz, getRandomInt(1, 20));
            Assertions.assertNotNull(sql, "must be not null");
            System.out.println(sql);
            System.out.println("=== 分隔线 ===");
        }
    }

    @RepeatedTest(2)
    @DisplayName("构建select max(id)语句")
    public void buildSelectMaxIdSqlTest(){
        for (Class<?> clazz : entityArray) {
            String fieldName = "id";
            String sql = sqlSqlBuilder.buildSelectMaxIdSql(clazz, fieldName);
            Assertions.assertNotNull(sql, "must be not null");
            System.out.println(sql);
            System.out.println("=== 分隔线 ===");
        }
    }



}
