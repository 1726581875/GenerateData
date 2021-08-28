package zhangyu.fool.generate.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.builder.SqlBuilder;
import zhangyu.fool.generate.object.FoolTable;

/**
 * @author xmz
 * @date: 2021/08/28
 */
public class MySqlExecutorTest {

    private SqlBuilder sqlBuilder;

    private SqlExecutor sqlExecutor;

    @BeforeEach
    void init() {
        sqlBuilder = new MySqlSqlBuilder();
        sqlExecutor = new MySqlExecutor();
    }

    @Test
    public void executeTest(){
        String sql = sqlBuilder.buildSelectMaxIdSql(FoolTable.class, "id");
        System.out.println(sql);
        Long maxId = (Long)sqlExecutor.execute(sql, Long.class);
        System.out.println(maxId);
    }



}
