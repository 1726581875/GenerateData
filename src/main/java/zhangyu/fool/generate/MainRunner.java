package zhangyu.fool.generate;
import zhangyu.fool.generate.object.FoolDatabase;
import zhangyu.fool.generate.runner.MySqlRunner;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MainRunner {


    public static void main(String[] args) {
        MySqlRunner mySqlRunner = new MySqlRunner();
        mySqlRunner.run(FoolDatabase.class, 100001);
    }

}
