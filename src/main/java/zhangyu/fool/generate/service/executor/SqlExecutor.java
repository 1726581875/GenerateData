package zhangyu.fool.generate.service.executor;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public interface SqlExecutor {

    /**
     * 执行SQL
     * @param sql
     */
    void execute(String sql);

    /**
     * 执行sql获取返回结果
     * @param sql
     * @param type
     * @return
     */
    Object execute(String sql, Class<?> type);

}
