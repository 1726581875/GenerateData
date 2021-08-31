package zhangyu.fool.generate;

import zhangyu.fool.generate.object.school.Sc;
import zhangyu.fool.generate.runner.MySqlRunner;

/**
 * @author xmz
 * @date: 2021/08/21
 */
public class MainTest {

    public static void main(String[] args) {
        MySqlRunner mySqlRunner = new MySqlRunner();
        mySqlRunner.toRun(Sc.class, 10);
    }

}
