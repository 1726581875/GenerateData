package zhangyu.fool.generate.service;

import org.junit.jupiter.api.BeforeEach;
import zhangyu.fool.generate.object.school.Course;
import zhangyu.fool.generate.object.school.Sc;
import zhangyu.fool.generate.object.school.Student;
import zhangyu.fool.generate.util.ConnectUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author xiaomingzhang
 * @date 2021/8/31
 */
public class BaseTest {

    private static final String SQL_SCRIPT_PATH = "src\\test\\resources\\school_test.sql";

    private static final String H2_URL = "jdbc:h2:mem:school_test;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1";

    private static final String H2_USERNAME = "sa";

    private static final String H2_PASSWORD = "sa";

    protected static final Class<?> [] entityArray = {Course.class, Sc.class, Student.class};

    private static final Random random = new Random();

    @BeforeEach
    void initDatabase() {

        //切换到H2数据源
        ConnectUtil.Config config = new ConnectUtil.Config(H2_URL,H2_USERNAME,H2_PASSWORD, ConnectUtil.H2_DRIVER);
        ConnectUtil.setConfig(config);

        //获取sql脚本
        String sqlScript = readSqlFile(SQL_SCRIPT_PATH);
        List<String> sqlList = splitSqlScript(sqlScript);

        //初始化H2数据库
        try(Connection conn = ConnectUtil.getConnection();
            Statement statement = conn.createStatement()){
            for (String sql : sqlList) {
                statement.execute(sql);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("======= 初始化sql完成 =======");

    }


    protected void foreachTest(Consumer<? super Class<?>> action){
        Arrays.asList(entityArray).forEach(action);
    }




    protected int getRandomInt(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    private String readSqlFile(String sqlFilePath) {
        StringBuilder sql = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(new File(sqlFilePath));
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            Stream<String> lines = bufferedReader.lines();
            lines.forEach(e -> sql.append(e).append("\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sql.toString();
    }

    private List<String> splitSqlScript(String content) {
        List<String> sqlList = new ArrayList<>();
        String[] split = content.split(";");
        for (String sql : split) {
            if (!"".equals(sql.trim())) {
                sqlList.add(sql + ";");
            }
        }
        return sqlList;
    }
}
