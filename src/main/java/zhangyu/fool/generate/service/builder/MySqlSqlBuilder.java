package zhangyu.fool.generate.service.builder;

import zhangyu.fool.generate.annotation.TableName;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.annotation.feild.Ignore;
import zhangyu.fool.generate.service.builder.model.AutoFieldRule;
import zhangyu.fool.generate.enums.IdType;
import zhangyu.fool.generate.service.random.factory.RandomFactory;
import zhangyu.fool.generate.util.NameUtil;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MySqlSqlBuilder implements SqlBuilder {

    private static final String INSERT_TEMPLATE = "insert into `%s`(%s) \n values %s";

    private static final String SELECT_FIELD_TEMPLATE = "select %s from `%s` limit %s,%s";

    private static final String SELECT_MAX_ID_TEMPLATE = "select max(%s) from `%s`";

    /**
     * mysql一次性插入数据受MySQL max_allowed_packet参数限制，为了不超过其阈值，设置最大批量大小
     */
    private static final int BATCH_NUM = 10000;

    @Override
    public String buildInsertSql(Class<?> entityClass, int rowNum) {
        return toBuildInsertSql(entityClass, null, rowNum);
    }

    @Override
    public String buildInsertSql(Class<?> entityClass, List<AutoFieldRule> ruleList, int rowNum) {
        return toBuildInsertSql(entityClass, ruleList, rowNum);
    }

    private String toBuildInsertSql(Class<?> entityClass, List<AutoFieldRule> ruleList, int limit) {

        if (limit > BATCH_NUM) {
            limit = BATCH_NUM;
        }

        String tableName = this.getTableNameSegment(entityClass);

        //获取列
        List<Field> fieldList = getNotIgnoreField(entityClass, ruleList);

        String fieldNames = this.getFieldSqlSegment(fieldList);

        String values = this.getValueSqlSegment(fieldList, ruleList, limit);

        return String.format(INSERT_TEMPLATE, tableName, fieldNames, values);
    }


    @Override
    public String buildSelectSql(Class<?> entityClass, String fieldName, int offset, int limit) {

        String tableNameSeg = this.getTableNameSegment(entityClass);

        String fieldNameSeg = getFieldSqlSegment(entityClass, fieldName);

        return String.format(SELECT_FIELD_TEMPLATE, fieldNameSeg, tableNameSeg, offset, limit);
    }

    @Override
    public String buildSelectMaxIdSql(Class<?> entityClass, String fieldName) {

        String tableNameSeg = this.getTableNameSegment(entityClass);

        String fieldNameSeg = getFieldSqlSegment(entityClass, fieldName);

        return String.format(SELECT_MAX_ID_TEMPLATE, fieldNameSeg, tableNameSeg);
    }

    private String getFieldSqlSegment(Class<?> entityClass, String fieldName) {
        Field field = null;
        try {
            field = entityClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return this.getFieldSqlSegment(Arrays.asList(field));
    }


    private String getTableNameSegment(Class<?> entityClass) {
        if (entityClass.getAnnotation(TableName.class) != null) {
            TableName annotation = entityClass.getAnnotation(TableName.class);
            if (!"".equals(annotation.value().trim())) {
                return annotation.value().trim();
            }
        }
        return NameUtil.convertToDataBaseRule(entityClass.getSimpleName());
    }

    private List<Field> getNotIgnoreField(Class<?> entityClass, List<AutoFieldRule> ruleList) {
        Field[] fields = entityClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>(fields.length);
        for (Field field : fields) {
            //过滤掉带有Ignore注解的列
            if (field.getAnnotation(Ignore.class) != null) {
                continue;
            }
            if (field.getAnnotation(Id.class) != null) {
                Id annotation = field.getAnnotation(Id.class);
                //主键是否交由数据库生成
                if (IdType.AUTH.equals(annotation.value())) {
                    if((ruleList == null || ruleList.size() == 0)){
                        continue;
                    }
                    Set<String> fieldSet = ruleList.stream().map(AutoFieldRule::getName).collect(Collectors.toSet());
                    if(!fieldSet.contains(field.getName())){
                        continue;
                    }
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
            fieldStr.append(NameUtil.around(fieldName, "`"));
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
    private String getValueSqlSegment(List<Field> fields, List<AutoFieldRule> relationFieldList, int limit) {

        Map<String, AutoFieldRule> fieldRuleMap = new HashMap<>(16);
        Map<String, Long> autoIdMap = new HashMap<>(16);

        initFieldRuleMap(relationFieldList, fieldRuleMap, autoIdMap);

        StringBuilder values = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            values.append("(");
            for (int j = 0; j < fields.size(); j++) {
                Object value = null;
                Field field = fields.get(j);
                //关联字段在范围内自增
                if (fieldRuleMap.containsKey(field.getName())) {
                    value = getNumberValueByRule(fieldRuleMap, autoIdMap, field.getName());
                } else {
                    //获取随机值
                    value = RandomFactory.getRandomValueType(field);
                }
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

    /**
     * 获取规则范围内数字
     * @param fieldRuleMap
     * @param relationIdMap
     * @param fieldName
     * @return
     */
    private Object getNumberValueByRule(Map<String, AutoFieldRule> fieldRuleMap, Map<String, Long> relationIdMap, String fieldName) {
        AutoFieldRule rule = fieldRuleMap.get(fieldName);
        Long value = relationIdMap.compute(fieldName, (k, v) -> {
            v = v + 1L;
            if (rule.getLimit() != null && v > rule.getLimit()) {
                v = rule.getAutoNum() + 1L;
            }
            return v;
        });
        return value;
    }


    private void initFieldRuleMap(List<AutoFieldRule> autoFieldRuleList, Map<String, AutoFieldRule> autoFieldRuleMap, Map<String, Long> autoIdMap) {
        if (autoFieldRuleList != null) {
            for (AutoFieldRule fieldRule : autoFieldRuleList) {
                autoFieldRuleMap.put(fieldRule.getName(), fieldRule);
                autoIdMap.put(fieldRule.getName(), fieldRule.getAutoNum());
            }
        }
    }

    private String convertValue(Object value) {
        String str = "";
        if (value instanceof String) {
            str = NameUtil.around((String) value, "'");
        } else if (value instanceof Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            str = NameUtil.around(dateFormat.format(value), "'");
        } else {
            str = String.valueOf(value);
        }
        return str;
    }

    public static void main(String[] args) {

    }

}
