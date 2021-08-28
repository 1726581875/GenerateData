package zhangyu.fool.generate.runner;

import zhangyu.fool.generate.annotation.feild.Join;
import zhangyu.fool.generate.builder.MySqlSqlBuilder;
import zhangyu.fool.generate.builder.SqlBuilder;
import zhangyu.fool.generate.executor.MySqlExecutor;
import zhangyu.fool.generate.executor.SqlExecutor;

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

    private SqlBuilder sqlBuilder;

    private SqlExecutor sqlExecutor;

    public MySqlRunner(){
        sqlBuilder = new MySqlSqlBuilder();
        sqlExecutor = new MySqlExecutor();
    }


    public void run(Class<?> entityClass, int rowNum){
        int pageSize = 10000;
        int pageNum = rowNum % 10000 == 0 ? rowNum / pageSize : rowNum / pageSize + 1;
        long beginTime = System.currentTimeMillis();
        MySqlSqlBuilder mySqlSqlBuilder = new MySqlSqlBuilder();
        for(int i = 1; i <= pageNum; i++) {

            int limit = i == pageNum ? rowNum - pageSize * (pageNum - 1) : pageSize;
            //构造SQL
            long buildStartTime = System.currentTimeMillis();
            String sql = mySqlSqlBuilder.buildInsertSql(entityClass, limit);
            System.out.println("第" + i + "页,数量= " + limit + ",构造SQL耗时=" + (System.currentTimeMillis() - buildStartTime) + "ms");

            //执行SQL
            long exeTime = System.currentTimeMillis();
            MySqlExecutor mySqlExector = new MySqlExecutor();
            mySqlExector.execute(sql);
            System.out.println("第" + i + "页,数量= " + limit + ",执行SQL耗时=" + (System.currentTimeMillis() - exeTime) + "ms");
        }
        System.out.println("总耗时=" + (System.currentTimeMillis() - beginTime) + "ms");
    }





    public static void main(String[] args) {

    }

    private void toRun(Class<?> entityClass, int rowNum){
        // init joinNodeList
        List<JoinTreeNode> joinNodeList = new ArrayList<>();
        JoinTreeNode parentNode = new JoinTreeNode(entityClass,null, 1, rowNum,null);
        joinNodeList.add(parentNode);
        doAnalyzeNodeTree(entityClass, parentNode, joinNodeList);

        // handler
        Collections.reverse(joinNodeList);
        for (JoinTreeNode joinTreeNode : joinNodeList){
            handleNode(joinTreeNode);
        }
    }

    void handleNode(JoinTreeNode node) {
        //主对象
        if(node.getJoinField() == null){

        }
        Class<?> type = getFieldType(node.getObjectClass(), node.getJoinField());
        if(Integer.class.equals(type) || Long.class.equals(type)){
            String selectSql = sqlBuilder.buildSelectMaxIdSql(node.getObjectClass(), node.getJoinField());
            Object result = sqlExecutor.execute(selectSql, type);
            Long maxId = result == null ? 0L : Long.valueOf(result.toString());
            //todo 待完善

        }else if(String.class.equals(type)){

        } else {
            throw new UnsupportedOperationException("不支持["+ type.getName() +"]类型的关联字段");
        }
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
                JoinTreeNode joinTreeNode = new JoinTreeNode(join.object(), join.field(), join.rel()
                        , join.rel() * parentNode.getRow(), parentNode);
                joinList.add(joinTreeNode);
                // 递归解析
                doAnalyzeNodeTree(join.object(), joinTreeNode, joinList);
            }
        }
    }





}
