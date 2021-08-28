package zhangyu.fool.generate.runner;

import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.executor.MySqlExector;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

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
            long buildStartTime = System.currentTimeMillis();
            String sql = mySqlSqlBuilder.buildInsertSql(entityClass, limit);
            System.out.println("第" + i + "页,数量= " + limit + ",构造SQL耗时=" + (System.currentTimeMillis() - buildStartTime) + "ms");

            //执行SQL
            long exeTime = System.currentTimeMillis();
            MySqlExector mySqlExector = new MySqlExector();
            mySqlExector.execute(sql);
            System.out.println("第" + i + "页,数量= " + limit + ",执行SQL耗时=" + (System.currentTimeMillis() - exeTime) + "ms");
        }
        System.out.println("总耗时=" + (System.currentTimeMillis() - beginTime) + "ms");
    }

    private void doHandler(Class<?> entityClass, List<Join> joinList) {
        Field[] fields = entityClass.getFields();
        for (Field field : fields){
            Join join = field.getAnnotation(Join.class);
            if(Objects.nonNull(join)) {
                joinList.add(join);
                Class<?> object = join.object();
                String joinField = join.field();
                //先构造关联对象的insert sql 并插入

                //查询获取关联对象的关联字段List

                //根据 关联字段 拼接当前对象sql并插入
                this.doHandler(object, joinList);
            }
        }

    }



}
