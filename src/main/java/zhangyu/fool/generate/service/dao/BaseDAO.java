package zhangyu.fool.generate.service.dao;

import java.util.List;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public interface BaseDAO {

    /**
     * 执行SQL
     * @param sql
     */
    void execute(String sql);

    /**
     * 获取查询数据结果列表
     * @param sql
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> getList(String sql, Class<T> type);

    /**
     * 获取查询结果
     * @param sql
     * @param type
     * @param <T>
     * @return
     */
    <T> T getOne(String sql, Class<T> type);


    <T> void createTableIfNotExist(Class<T> entity);



}
