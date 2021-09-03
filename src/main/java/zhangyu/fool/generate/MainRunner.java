package zhangyu.fool.generate;

import zhangyu.fool.generate.object.test.mysql.FoolTable;
import zhangyu.fool.generate.service.runner.MySqlRunner;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MainRunner {

    public static void main(String[] args) {
        MySqlRunner mySqlRunner = new MySqlRunner();
        mySqlRunner.toRun(FoolTable.class, 5);
    }

}
