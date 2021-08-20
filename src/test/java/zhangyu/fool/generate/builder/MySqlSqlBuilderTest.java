package zhangyu.fool.generate.builder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.object.FoolDatabase;

/**
 * @author xmz
 * @date: 2021/08/19
 */
public class MySqlSqlBuilderTest {

    @Test
    public void buildInsertSqlTest() {
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        String sql = mySqlSqlBuilder.buildInsertSql(FoolDatabase.class, 100);
        Assertions.assertNotNull(sql, "must be not null");
        System.out.println(sql);
    }



}
