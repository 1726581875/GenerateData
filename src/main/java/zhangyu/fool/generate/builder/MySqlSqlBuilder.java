package zhangyu.fool.generate.builder;

import zhangyu.fool.generate.FoolDatabase;
import zhangyu.fool.generate.random.FoolRandom;
import zhangyu.fool.generate.random.factory.RandomFactory;
import zhangyu.fool.generate.util.NameConvertUtil;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MySqlSqlBuilder implements SqlBuilder {

    private static final String INSERT_TEMPLATE = "insert into `%s`(%s) \n values %s";

    @Override
    public String buildInsertSql(Class<?> entityClass) {

        String tableName = this.getTableNameSegment(entityClass);

        String fieldNames = this.getFieldSegment(entityClass);

        String values = this.getValueSegment(entityClass, 10);

        return String.format(INSERT_TEMPLATE, tableName, fieldNames, values);
    }


    private String getTableNameSegment(Class<?> entityClass){
        return NameConvertUtil.convertToDataBaseRule(entityClass.getSimpleName());
    }
    /**
     * 返回表sql列字段
     * 例如：user_id,name,password,create_time
     *
     * @param entityClass
     * @return
     */
    private String getFieldSegment(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder fieldStr = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            String fieldName = NameConvertUtil.convertToDataBaseRule(fields[i].getName());
            fieldStr.append("`" + fieldName + "`");
            if (i != fields.length - 1) {
                fieldStr.append(",");
            }
        }
        return fieldStr.toString();
    }

    /**
     * 返回值字段
     * 如：(1,'张三','1234567','2021-08-19'),(2,'李四','1234567','2021-08-20');
     *
     * @param entityClass
     * @param limit  条数限制
     * @return
     */
    private String getValueSegment(Class<?> entityClass, int limit) {
        Field[] fields = entityClass.getDeclaredFields();
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            values.append("(");
            for (int j = 0; j < fields.length; j++) {
                //获取随机值
                Class<?> type = fields[j].getType();
                FoolRandom random = RandomFactory.getByType(type);
                Object value = random.randomValue(fields[j]);
                //转换并拼接
                values.append(convertValue(value));
                if(j != fields.length - 1) {
                    values.append(",");
                }
            }
            values.append(")");
            if(i != limit - 1) {
                values.append("," + "\n");
            }else {
                values.append(";");
            }
        }
        return values.toString();
    }

    private String convertValue(Object value){
        String str = "";
        if(value instanceof String){
            str = "'" + value + "'";
        }else if(value instanceof Date){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = "'" + dateFormat.format(value) + "'";
        } else {
            str = String.valueOf(value);
        }
        return str;
    }

    public static void main(String[] args) {
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        String sql = mySqlSqlBuilder.buildInsertSql(FoolDatabase.class);
        System.out.println(sql);
    }


}
