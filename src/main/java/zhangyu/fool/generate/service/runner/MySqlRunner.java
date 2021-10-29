package zhangyu.fool.generate.service.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.service.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.service.builder.SqlBuilder;
import zhangyu.fool.generate.service.builder.model.AutoFieldRule;
import zhangyu.fool.generate.service.executor.MySqlExecutor;
import zhangyu.fool.generate.service.executor.SqlExecutor;
import zhangyu.fool.generate.service.runner.model.TableNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaomingzhang
 * @date 2021/8/23
 */
public class MySqlRunner {

    private static final Logger log = LoggerFactory.getLogger(MySqlRunner.class);

    private SqlBuilder sqlBuilder;

    private SqlExecutor sqlExecutor;

    public MySqlRunner() {
        sqlBuilder = new MySqlSqlBuilder();
        sqlExecutor = new MySqlExecutor();
    }


    /**
     * 仅仅单表数据生成
     * @param entityClass
     * @param rowNum
     */
    public void run(Class<?> entityClass, int rowNum) {
        List<String> sqlList = buildInsertSql(entityClass, null, rowNum);
        //执行SQL
        long exeTime = System.currentTimeMillis();
        sqlList.forEach(sqlExecutor::execute);
        System.out.println("执行sql耗时：" + (System.currentTimeMillis() - exeTime));
    }

    /**
     * 支持多表有关联字段数据生成
     * todo 目前仅支持自增id作为关联字段
     * @param entityClass
     * @param rowNum
     */
    public void batchGenerateData(Class<?> entityClass, int rowNum) {
        // 初始化根节点
        List<TableNode> tableNodeList = new ArrayList<>();
        TableNode parentNode = new TableNode(entityClass, null, 1, rowNum, null);
        tableNodeList.add(parentNode);
        // 递归解析所有的@Join注解的列
        doAnalyzeTableNodeTree(entityClass, parentNode, tableNodeList);

        List<String> insertSqlList = new ArrayList<>(16);
        // handler and get sql
        for (TableNode tableNode : tableNodeList) {
            insertSqlList.addAll(handleTableNode(tableNode));
        }

        // run sql
        insertSqlList.forEach(sqlExecutor::execute);

    }

    private List<String> handleTableNode(TableNode node) {

        List<AutoFieldRule> autoFieldRules = getAutoFieldRuleList(node);
        // 如果是根节点,则不需要考虑主键规则
        if (node.isRootNode()) {
            return buildInsertSql(node.getObjectClass(), autoFieldRules, node.getRow());
        }

        Class<?> type = getFieldType(node.getObjectClass(), node.getJoinField());
        if (Integer.class.equals(type) || Long.class.equals(type)) {
            return buildInsertSql(node.getObjectClass(), autoFieldRules, node.getRow());
        } else if (String.class.equals(type)) {
            throw new UnsupportedOperationException("不支持[" + type.getName() + "]类型的关联字段");
        } else {
            throw new UnsupportedOperationException("不支持[" + type.getName() + "]类型的关联字段");
        }
    }


    private List<String> buildInsertSql(Class<?> entityClass, List<AutoFieldRule> autoFieldRules, int rowNum) {

        // 超过10000行则分批
        int pageSize = MySqlSqlBuilder.BATCH_NUM;
        int pageNum = rowNum % pageSize == 0 ? rowNum / pageSize : rowNum / pageSize + 1;
        List<String> sqlList = new ArrayList<>(pageNum);
        long beginTime = System.currentTimeMillis();

        for (int i = 1; i <= pageNum; i++) {
            int limit = i == pageNum ? rowNum - pageSize * (pageNum - 1) : pageSize;
            //构造SQL
            String sql = autoFieldRules == null ? sqlBuilder.buildInsertSql(entityClass, limit)
                    : sqlBuilder.buildInsertSql(entityClass, autoFieldRules, limit);
            log.debug("build sql: {}", sql);
            log.debug("=====  分隔线  =====");
            sqlList.add(sql);
        }
        log.debug("构造SQL耗时={}ms，数量={}", (System.currentTimeMillis() - beginTime), rowNum);
        return sqlList;
    }

    private List<AutoFieldRule> getAutoFieldRuleList(TableNode node) {

        // 不是根节点，获取当前表主键自增的最大值
        List<AutoFieldRule> autoFieldRules = new ArrayList<>();
        if (node.notIsRootNode()) {
            Class<?> type = getFieldType(node.getObjectClass(), node.getJoinField());
            Long maxId = getAutoMaxId(node, type);
            autoFieldRules.add(new AutoFieldRule(node.getJoinField(), maxId, null));
        }
        // 如果存在关联字段，则获取关联字段生成规则
        if (node.getChildrenNode() != null && node.getChildrenNode().size() > 0) {
            for (TableNode child : node.getChildrenNode()) {
                Class<?> childType = getFieldType(child.getObjectClass(), child.getJoinField());
                // 关联表的最大id，以及其自增范围
                Long childMaxId = getAutoMaxId(child, childType);
                autoFieldRules.add(new AutoFieldRule(child.getBindField(), childMaxId, childMaxId + child.getRow()));
            }
        }
        return autoFieldRules;
    }


    private Long getAutoMaxId(TableNode node, Class<?> type) {
        String selectSql = sqlBuilder.buildSelectMaxIdSql(node.getObjectClass(), node.getJoinField());
        Object result = sqlExecutor.getOne(selectSql, type);
        return result == null ? 0L : Long.valueOf(result.toString());
    }


    private Class<?> getFieldType(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }
    }


    private void doAnalyzeTableNodeTree(Class<?> entityClass, TableNode parentNode, List<TableNode> joinList) {
        Field[] fields = entityClass.getDeclaredFields();
        List<TableNode> childrenNode = new ArrayList<>();
        for (Field field : fields) {
            Join join = field.getAnnotation(Join.class);
            if (Objects.nonNull(join)) {
                // 当前节点对应表需要生成数据的行数
                int row = (parentNode.getRow() / join.rel()) > 0 ? parentNode.getRow() / join.rel() : 1;
                TableNode joinTreeNode = new TableNode(join.object(), join.field(), join.rel(), row, field.getName());
                // set childrenNode
                childrenNode.add(joinTreeNode);
                joinList.add(joinTreeNode);
                // 递归解析
                doAnalyzeTableNodeTree(join.object(), joinTreeNode, joinList);
            }
        }
        parentNode.setChildrenNode(childrenNode);
    }


}
