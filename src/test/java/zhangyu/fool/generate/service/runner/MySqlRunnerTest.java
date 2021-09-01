package zhangyu.fool.generate.service.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.service.BaseTest;

/**
 * @author xiaomingzhang
 * @date 2021/9/1
 */
public class MySqlRunnerTest extends BaseTest {

    private MySqlRunner sqlRunner;

    @BeforeEach
    public void init(){
        sqlRunner = new MySqlRunner();
    }

    @Test
    public void runnerTest(){
        foreachTest(clazz -> sqlRunner.toRun(clazz, getRandomInt(1,20)));
    }



}
