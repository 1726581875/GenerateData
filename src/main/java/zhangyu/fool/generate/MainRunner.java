package zhangyu.fool.generate;

import zhangyu.fool.generate.object.test.mysql.FoolTable;
import zhangyu.fool.generate.service.runner.MySqlDataInsertRunner;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MainRunner {

    public static void main(String[] args) {
        MySqlDataInsertRunner mySqlRunner = new MySqlDataInsertRunner();
        mySqlRunner.batchGenerateData(FoolTable.class, 5);

    }

}
