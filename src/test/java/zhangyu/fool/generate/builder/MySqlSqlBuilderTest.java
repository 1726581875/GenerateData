package zhangyu.fool.generate.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.object.FoolDatabase;

/**
 * @author xmz
 * @date: 2021/08/19
 */
@DisplayName("A build sql test case")
public class MySqlSqlBuilderTest {

    @DisplayName("A build sql test case2")
    @Test
    public void buildInsertSqlTest() {
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        String sql = mySqlSqlBuilder.buildInsertSql(FoolDatabase.class, 100);
        Assertions.assertNotNull(sql, "must be not null");
        System.out.println(sql);
    }



}
