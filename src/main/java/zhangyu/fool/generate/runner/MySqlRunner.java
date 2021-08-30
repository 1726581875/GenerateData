package zhangyu.fool.generate.runner;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.builder.SqlBuilder;
import zhangyu.fool.generate.builder.model.AutoFieldRule;
import zhangyu.fool.generate.executor.MySqlExecutor;
import zhangyu.fool.generate.executor.SqlExecutor;
import zhangyu.fool.generate.runner.model.JoinTreeNode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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

    public MySqlRunner(){
        sqlBuilder = new MySqlSqlBuilder();
        sqlExecutor = new MySqlExecutor();
    }


    public void run(Class<?> entityClass, int rowNum){
       run(entityClass, null, rowNum);
    }

    public void toRun(Class<?> entityClass, int rowNum){
        // init joinNodeList
        List<JoinTreeNode> joinNodeList = new ArrayList<>();
        JoinTreeNode parentNode = new JoinTreeNode(entityClass,null, 1, rowNum);
        joinNodeList.add(parentNode);
        doAnalyzeNodeTree(entityClass, parentNode, joinNodeList);

        // handler
        Collections.reverse(joinNodeList);
        for (JoinTreeNode joinTreeNode : joinNodeList){
            handleNode(joinTreeNode);
        }
    }

    void handleNode(JoinTreeNode node) {

        List<AutoFieldRule> autoFieldRules = getAutoFieldRuleList(node);
        //主对象,不需要考虑主键，直接生成
        if(node.getJoinField() == null) {
            run(node.getObjectClass(), autoFieldRules, node.getRow());
            return;
        }

        Class<?> type = getFieldType(node.getObjectClass(), node.getJoinField());
        if(Integer.class.equals(type) || Long.class.equals(type)){
            run(node.getObjectClass(), autoFieldRules, node.getRow());
        }else if(String.class.equals(type)){

        } else {
            throw new UnsupportedOperationException("不支持["+ type.getName() +"]类型的关联字段");
        }
    }

    private void run(Class<?> entityClass, List<AutoFieldRule> autoFieldRules, int rowNum){
        int pageSize = 10000;
        int pageNum = rowNum % 10000 == 0 ? rowNum / pageSize : rowNum / pageSize + 1;
        long beginTime = System.currentTimeMillis();
        for(int i = 1; i <= pageNum; i++) {

            int limit = i == pageNum ? rowNum - pageSize * (pageNum - 1) : pageSize;
            //构造SQL
            long buildStartTime = System.currentTimeMillis();
            String sql = autoFieldRules == null ? sqlBuilder.buildInsertSql(entityClass, limit)
                    : sqlBuilder.buildInsertSql(entityClass, autoFieldRules, limit);
            log.debug("第{}页, 数量={}, 构造SQL耗时={}ms", i, limit, (System.currentTimeMillis() - buildStartTime));

            System.out.println(sql);
            System.out.println("======================");

            //执行SQL
            long exeTime = System.currentTimeMillis();
            //sqlExecutor.execute(sql);
            log.debug("第{}页, 数量={}, 执行SQL耗时={}ms", i, limit, (System.currentTimeMillis() - exeTime));
        }
        log.debug("总耗时={}ms", (System.currentTimeMillis() - beginTime));
    }

    private List<AutoFieldRule> getAutoFieldRuleList(JoinTreeNode node){

        // 主键字段
        List<AutoFieldRule> autoFieldRules = new ArrayList<>();
        if(node.getJoinField() != null) {
            Class<?> type = getFieldType(node.getObjectClass(), node.getJoinField());
            Long maxId = getAutoMaxId(node, type);
            autoFieldRules.add(new AutoFieldRule(node.getJoinField(), maxId, null));
        }
        // 关联字段
        if(node.getChildrenNode() != null && node.getChildrenNode().size() > 0) {
            for (JoinTreeNode child : node.getChildrenNode()) {
                Class<?> childType = getFieldType(child.getObjectClass(), child.getJoinField());
                Long childMaxId = getAutoMaxId(child, childType);
                autoFieldRules.add(new AutoFieldRule(child.getBindField(), childMaxId, childMaxId + child.getRow()));
            }
        }
        return autoFieldRules;
    }


    private Long getAutoMaxId(JoinTreeNode node, Class<?> type){
        String selectSql = sqlBuilder.buildSelectMaxIdSql(node.getObjectClass(), node.getJoinField());
        Object result = sqlExecutor.execute(selectSql, type);
        return result == null ? 0L : Long.valueOf(result.toString());
    }


    private Class<?> getFieldType(Class<?> clazz, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e);
        }
    }




    private void doAnalyzeNodeTree(Class<?> entityClass, JoinTreeNode parentNode, List<JoinTreeNode> joinList) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields){
            Join join = field.getAnnotation(Join.class);
            if(Objects.nonNull(join)) {
                int row = (parentNode.getRow() / join.rel()) > 0L ? parentNode.getRow() / join.rel() : 1;
                JoinTreeNode joinTreeNode = new JoinTreeNode(join.object(), join.field(), join.rel(), row);
                joinTreeNode.setBindField(field.getName());
                // set childrenNode
                List<JoinTreeNode> childrenNode = parentNode.getChildrenNode();
                if(childrenNode == null) {
                    childrenNode = new ArrayList<>();
                }
                childrenNode.add(joinTreeNode);
                parentNode.setChildrenNode(childrenNode);

                joinList.add(joinTreeNode);
                // 递归解析
                doAnalyzeNodeTree(join.object(), joinTreeNode, joinList);
            }
        }
    }




}