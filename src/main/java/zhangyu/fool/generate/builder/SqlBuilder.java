package zhangyu.fool.generate.builder;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public interface SqlBuilder {

    /**
     * 构建插入语句
     * @param entityClass 对应实体类class
     * @param rowNum 行数
     * @return
     */
    String buildInsertSql(Class<?> entityClass, int rowNum);

}
