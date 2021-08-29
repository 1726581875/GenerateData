package zhangyu.fool.generate.builder;

import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.builder.model.AutoFieldRule;

import java.util.List;

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

    String buildInsertSql(Class<?> entityClass, List<AutoFieldRule> ruleList, int rowNum);

    /**
     * 构建查询语句
     * @param entityClass 实体类class
     * @param fieldName 列名
     * @param offset
     * @param limit
     * @return
     */
    String buildSelectSql(Class<?> entityClass, String fieldName, int offset, int limit);


    String buildSelectMaxIdSql(Class<?> entityClass, String fieldName);

}
