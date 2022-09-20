package zhangyu.fool.generate.service.dao;

import com.mysql.cj.jdbc.ConnectionImpl;
import zhangyu.fool.generate.annotation.feild.Id;
import zhangyu.fool.generate.exception.RunSqlException;
import zhangyu.fool.generate.util.ConnectUtil;
import zhangyu.fool.generate.util.NameUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MySqlDAO implements BaseDAO {

    private final static Set<Class<?>> supportTypeSet = new HashSet<>(Arrays.asList(String.class, Date.class));

    private static final String CREATE_TABLE_SQL_TEMPLATE = "create table `%s` (\n%s\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";


    @Override
    public void execute(String sql) {
        try (Connection connection = ConnectUtil.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RunSqlException("execute sql execute", e);
        }
    }


    @Override
    public <T> List<T> getList(String sql, Class<T> type) {

        List<T> resultList = new ArrayList<>();
        try (Connection connection = ConnectUtil.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql);
             ResultSet resultSet = prepareStatement.executeQuery()) {
            while (resultSet.next()) {
                resultList.add(analyzeResult(resultSet, type));
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RunSqlException(e.getMessage(), e);
        }
    }

    private <T> T analyzeResult(ResultSet resultSet, Class<T> type) throws Exception {
        if (isMappingSupportType(type)) {
            return (T)resultSet.getObject(1);
        } else {
            T resultInstance = type.getConstructor().newInstance();
            Field[] declaredFields = type.getDeclaredFields();
            Set<String> tableColumnNameSet = getTableColumnNameSet(resultSet);
            for (Field field : declaredFields) {
                if (isMappingSupportType(field.getType())) {
                    // java列名转数据库命名规则，按驼峰对应“_”规则转换
                    String fieldName = NameUtil.convertToDataBaseRule(field.getName());
                    // 字段匹配，存在的列才获取结果并赋值,不存在的列则不做处理保持为null
                    if (tableColumnNameSet.contains(fieldName)) {
                        Object value = resultSet.getObject(fieldName);
                        if (Objects.nonNull(value)) {
                            field.setAccessible(true);
                            field.set(resultInstance, convertValue(field, value));
                        }
                    }
                }
            }
            return resultInstance;
        }
    }


    private Object convertValue(Field field, Object value) {
        // h2数据库tinyint查询结果对应java的byte类型，想使用Integer接收在此处做转换
        if (value instanceof Byte) {
            if(field.getType().equals(Integer.class) || field.getType().equals(Integer.TYPE)) {
                value = ((Byte) value).intValue();
            }
        }
        return value;
    }


    @Override
    public <T> T getOne(String sql, Class<T> type) {
        List<T> resultList = getList(sql, type);
        if (resultList.size() == 0) {
            return null;
        }
        if (resultList.size() > 1) {
            throw new RuntimeException("返回结果行数大于1, 行数为" + resultList.size() + "");
        }
        return resultList.get(0);
    }

    private Set<String> getTableColumnNameSet(ResultSet resultSet) throws SQLException {
        Set<String> fieldNameSet = new HashSet<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            fieldNameSet.add(metaData.getColumnLabel(i));
        }
        return fieldNameSet;
    }

    /**
     * 获取数据库支持的映射类型
     *
     * @param clazz
     * @return
     */
    public static boolean isMappingSupportType(Class clazz) {
        return isBaseType(clazz) || supportTypeSet.contains(clazz);
    }

    /**
     * 判断是否是基础数据类型或者基础类型的包装类型
     *
     * @param clazz
     * @return
     */
    public static boolean isBaseType(Class clazz) {
        try {
            return clazz.isPrimitive() || ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String sql = "select count(*) from fool_database";
        MySqlDAO mySqlExecutor = new MySqlDAO();
        Integer count = mySqlExecutor.getOne(sql, Integer.class);
        System.out.println(count);
    }



    @Override
    public <T> void createTableIfNotExist(Class<T> entity) {
        // 如果表不存在，则创建表
        if (!isExistTable(entity)) {
            createTable(entity);
        }
    }

    public <T> void createTable(Class<T> entity) {
        // 构造建表sql
        String createTableSql = getCreateTableSql(entity);
        // 执行sql
        exec(createTableSql);
    }


    private boolean isExistTable(Class<?> entity) {
        Connection connection = ConnectUtil.getConnection();
        try {
            String schema = ((ConnectionImpl) connection).getDatabase();
            String tableName = getTableName(entity);
            String querySql = "SELECT COUNT(*) FROM information_schema.TABLES " +
                    "WHERE table_schema = '" + schema + "' and table_name ='" + tableName + "'";
            Long resultNum = getOne(querySql, Long.class);
            return resultNum != 0L;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }



    public boolean exec(String sql) {
        try (Connection conn = ConnectUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            return statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("sql执行失败:" + e.getMessage());
        }
    }


    private String getCreateTableSql(Class<?> entity) {
        // 获取表名
        String tableName = getTableName(entity);

        Field[] fields = entity.getDeclaredFields();
        /*
         * 参数1：列名，参数2：列类型
         * 如：`username` varchar(20),
         */
        final String template = "`%s` %s,\n";
        StringBuilder fieldSql = new StringBuilder();
        String idFieldName = "";
        for (Field field : fields) {
            String databaseFieldName = null;

            String fieldType = null;

            // 解析TableField注解获取字段名、字段类型规则
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null) {
                databaseFieldName = "".equals(tableField.value()) ? NameUtil.convertToDataBaseRule(field.getName()) : tableField.value();
                fieldType = "".equals(tableField.type()) ? getDatabaseType(field.getType()) : tableField.type();
            } else {
                databaseFieldName = NameUtil.convertToDataBaseRule(field.getName());
                fieldType = getDatabaseType(field.getType());
            }

            // 如果是主键，设置自增
            if (Objects.nonNull(field.getAnnotation(Id.class))) {
                idFieldName = databaseFieldName;
                fieldType = fieldType + " NOT NULL AUTO_INCREMENT";
            }
            if (Objects.nonNull(field.getAnnotation(DateAuto.class))) {
                DateAuto annotation = field.getAnnotation(DateAuto.class);
                fieldType = fieldType + ("update".equals(annotation.value()) ?
                        " NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
                        : " NOT NULL DEFAULT CURRENT_TIMESTAMP");
            }
            fieldSql.append(String.format(template, databaseFieldName, fieldType));
        }
        fieldSql.append("PRIMARY KEY (`" + idFieldName + "`)");

        // 组装完整的建表sql
        return String.format(CREATE_TABLE_SQL_TEMPLATE, tableName, fieldSql.toString());
    }

    /**
     * 获取获取表名
     *
     * @param entity
     * @return
     */
    private String getTableName(Class<?> entity) {
        return NameUtil.convertToDataBaseRule(entity.getSimpleName());
    }

    private String getDatabaseType(Class<?> type) {
        if (String.class == type) {
            return "varchar(255)";
        } else if (Integer.class == type) {
            return "int";
        } else if (Long.class == type) {
            return "bigint";
        } else if (Date.class == type) {
            return "datetime";
        }
        return "varchar(250)";
    }

}
