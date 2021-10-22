package zhangyu.fool.generate.service.executor;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.h2.jdbc.JdbcResultSet;
import zhangyu.fool.generate.exception.RunSqlException;
import zhangyu.fool.generate.object.test.mysql.FoolDatabase;
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
public class MySqlExecutor implements SqlExecutor {

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
                T resultItem = analyzeResult(resultSet, type);
                resultList.add(resultItem);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RunSqlException(e.getMessage(), e);
        }
    }

    private <T> T analyzeResult(ResultSet resultSet, Class<T> type) throws Exception {
        if (isSupportType(type)) {
            return resultSet.getObject(1, type);
        } else {
            T resultInstance = type.getConstructor().newInstance();
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields) {
                if (isSupportType(field.getType())) {
                    // 字段命名必须驼峰对应
                    String fieldName = NameUtil.convertToDataBaseRule(field.getName());
                    Set<String> tableColumnNameSet = getTableColumnNameSet(resultSet);
                    // 字段匹配
                    if (tableColumnNameSet.contains(fieldName)) {
                        Object value = resultSet.getObject(fieldName);
                        if (Objects.nonNull(value)) {
                            field.setAccessible(true);
                            field.set(resultInstance, value);
                        }
                    }
                }
            }
            return resultInstance;
        }
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

    private Set<String> getTableColumnNameSet(ResultSet resultSet) {
        Set<String> fieldNameSet = new HashSet<>();
        if(resultSet instanceof ResultSetImpl) {
            com.mysql.cj.result.Field[] fields = ((ResultSetImpl)resultSet).getColumnDefinition().getFields();
            for (com.mysql.cj.result.Field field : fields) {
                fieldNameSet.add(field.getOriginalName());
            }
            // todo 需要兼容h2数据库连接驱动
        } else if(resultSet instanceof JdbcResultSet){

        }
        return fieldNameSet;
    }

    /**
     * 获取数据库支持的映射类型
     *
     * @param clazz
     * @return
     */
    public static boolean isSupportType(Class clazz) {
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
        String sql = "select * from fool_database limit 10";
        MySqlExecutor mySqlExecutor = new MySqlExecutor();
        List<FoolDatabase> foolDatabase = mySqlExecutor.getList(sql, FoolDatabase.class);
        foolDatabase.forEach(System.out::println);
    }
}
