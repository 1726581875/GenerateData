package zhangyu.fool.generate;

import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.executor.MySqlExector;
import zhangyu.fool.generate.object.FoolDatabase;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MainRunner {


    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        String sql = mySqlSqlBuilder.buildInsertSql(FoolDatabase.class,10000);
        System.out.println("构造SQL耗时=" + (System.currentTimeMillis() - beginTime) + "ms");

        long exeTime = System.currentTimeMillis();
        MySqlExector mySqlExector = new MySqlExector();
        mySqlExector.execute(sql);
        System.out.println("执行SQL耗时=" + (System.currentTimeMillis() - exeTime) + "ms");


        System.out.println("总耗时=" + (System.currentTimeMillis() - beginTime) + "ms");
    }

}
