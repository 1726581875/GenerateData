package zhangyu.fool.generate.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.object.FoolDatabase;

/**
 * @author xmz
 * @date: 2021/08/19
 */
@DisplayName("MySqlSqlBuilderTest")
public class MySqlSqlBuilderTest {

    private SqlBuilder sqlSqlBuilder;

    @BeforeEach
    void init() {
        sqlSqlBuilder = new MySqlSqlBuilder();
    }


    @DisplayName("buildInsertSqlTest")
    @Test
    public void buildInsertSqlTest() {
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        String sql = mySqlSqlBuilder.buildInsertSql(FoolDatabase.class, 100);
        Assertions.assertNotNull(sql, "must be not null");
        System.out.println(sql);
    }

    @Test
    public void buildSelectMaxIdSqlTest(){
        String fieldName = "id";
        String sql = sqlSqlBuilder.buildSelectMaxIdSql(FoolDatabase.class, fieldName);
        Assertions.assertNotNull(sql, "must be not null");
        System.out.println(sql);
    }



}
