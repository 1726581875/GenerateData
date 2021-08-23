package zhangyu.fool.generate.runner;

import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.executor.MySqlExector;
/**
 * @author xiaomingzhang
 * @date 2021/8/23
 */
public class MySqlRunner {

    public void run(Class<?> entityClass, int rowNum){
        int pageSize = 10000;
        int pageNum = rowNum % 10000 == 0 ? rowNum / pageSize : rowNum / pageSize + 1;
        long beginTime = System.currentTimeMillis();
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        for(int i = 1; i <= pageNum; i++) {

            int limit = i == pageNum ? rowNum - pageSize * (pageNum - 1) : pageSize;
            //构造SQL
            String sql = mySqlSqlBuilder.buildInsertSql(entityClass, limit);
            System.out.println("第" + i + "页,数量= " + limit + ",构造SQL耗时=" + (System.currentTimeMillis() - beginTime) + "ms");

            //执行SQL
            long exeTime = System.currentTimeMillis();
            MySqlExector mySqlExector = new MySqlExector();
            mySqlExector.execute(sql);
            System.out.println("第" + i + "页,数量= " + limit + ",执行SQL耗时=" + (System.currentTimeMillis() - exeTime) + "ms");
        }
        System.out.println("总耗时=" + (System.currentTimeMillis() - beginTime) + "ms");
    }


}
