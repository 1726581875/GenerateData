package zhangyu.fool.generate;

import zhangyu.fool.generate.object.test.mysql.UserTest;
import zhangyu.fool.generate.service.dao.BaseDAO;
import zhangyu.fool.generate.service.dao.MySqlDAO;
import zhangyu.fool.generate.service.runner.MySqlDataInsertRunner;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class MainRunner {

    public static void main(String[] args) {
        MySqlDataInsertRunner mySqlRunner = new MySqlDataInsertRunner();
        BaseDAO baseDAO = new MySqlDAO();

        baseDAO.createTableIfNotExist(UserTest.class);
        Long beginTime = System.currentTimeMillis();
        mySqlRunner.batchGenerateData(UserTest.class, 10000000);

        System.out.println("耗时=" + (System.currentTimeMillis() - beginTime) / 1000  + "s");
    }

}
