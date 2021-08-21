package zhangyu.fool.generate.builder;

import zhangyu.fool.generate.annotation.TableName;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Ignore;
import zhangyu.fool.generate.enums.IdType;
import zhangyu.fool.generate.object.FoolDatabase;
import zhangyu.fool.generate.random.FoolRandom;
import zhangyu.fool.generate.random.factory.RandomFactory;
import zhangyu.fool.generate.util.NameUtil;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MySqlSqlBuilder implements SqlBuilder {

    private static final String INSERT_TEMPLATE = "insert into `%s`(%s) \n values %s";

    /**
     * mysql一次性插入数据受MySQL max_allowed_packet参数限制，为了不超过其阈值，设置最大批量大小
     */
    private static final int BATCH_NUM = 10000;

    @Override
    public String buildInsertSql(Class<?> entityClass, int rowNum) {

        if(rowNum > BATCH_NUM) {
            rowNum = BATCH_NUM;
        }

        String tableName = this.getTableNameSegment(entityClass);

        //获取列
        List<Field> fieldList = getNotIgnoreField(entityClass);

        String fieldNames = this.getFieldSqlSegment(fieldList);

        String values = this.getValueSqlSegment(fieldList, rowNum);

        return String.format(INSERT_TEMPLATE, tableName, fieldNames, values);
    }


    private String getTableNameSegment(Class<?> entityClass) {
        if(entityClass.getAnnotation(TableName.class) != null) {
            TableName annotation = entityClass.getAnnotation(TableName.class);
            if(!"".equals(annotation.value().trim())){
                return annotation.value().trim();
            }
        }
        return NameUtil.convertToDataBaseRule(entityClass.getSimpleName());
    }

    private List<Field> getNotIgnoreField(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>(fields.length);
        for (Field field : fields) {
            //过滤掉带有Ignore注解的列
            if (field.getAnnotation(Ignore.class) != null) {
                continue;
            }
            //过滤掉id自增列
            if (field.getAnnotation(Id.class) != null) {
                Id annotation = field.getAnnotation(Id.class);
                if (IdType.AUTH.equals(annotation.value())) {
                    continue;
                }
            }
            fieldList.add(field);
        }
        return fieldList;
    }


    /**
     * 返回表sql列字段
     * 例如：user_id,name,password,create_time
     *
     * @param fields
     * @return
     */
    private String getFieldSqlSegment(List<Field> fields) {

        StringBuilder fieldStr = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            String fieldName = NameUtil.convertToDataBaseRule(fields.get(i).getName());
            fieldStr.append("`" + fieldName + "`");
            if (i != fields.size() - 1) {
                fieldStr.append(",");
            }
        }
        return fieldStr.toString();
    }

    /**
     * 返回值字段
     * 如：(1,'张三','1234567','2021-08-19'),(2,'李四','1234567','2021-08-20');
     *
     * @param fields
     * @param limit  条数限制
     * @return
     */
    private String getValueSqlSegment(List<Field> fields, int limit) {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            values.append("(");
            for (int j = 0; j < fields.size(); j++) {
                //获取随机值
                Class<?> type = fields.get(j).getType();
                FoolRandom random = RandomFactory.getByType(type);
                Object value = random.randomValue(fields.get(j));
                //转换并拼接值
                values.append(convertValue(value));
                if (j != fields.size() - 1) {
                    values.append(",");
                }
            }
            values.append(")");
            if (i != limit - 1) {
                values.append("," + "\n");
            } else {
                values.append(";");
            }
        }
        return values.toString();
    }

    private String convertValue(Object value) {
        String str = "";
        if (value instanceof String) {
            str = "'" + value + "'";
        } else if (value instanceof Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = "'" + dateFormat.format(value) + "'";
        } else {
            str = String.valueOf(value);
        }
        return str;
    }

    public static void main(String[] args) {
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        String sql = mySqlSqlBuilder.buildInsertSql(FoolDatabase.class,100);
        System.out.println(sql);
    }

}
