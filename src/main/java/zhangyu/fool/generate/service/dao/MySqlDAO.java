package zhangyu.fool.generate.service.dao;

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
            return resultSet.getObject(1, type);
        } else {
            T resultInstance = type.getConstructor().newInstance();
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields) {
                if (isMappingSupportType(field.getType())) {
                    // java列名转数据库命名规则，按驼峰对应“_”规则转换
                    String fieldName = NameUtil.convertToDataBaseRule(field.getName());
                    Set<String> tableColumnNameSet = getTableColumnNameSet(resultSet);
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
}
