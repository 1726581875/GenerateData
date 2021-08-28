package zhangyu.fool.generate.executor;

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

    Object execute(String sql, Class<?> type);

}
