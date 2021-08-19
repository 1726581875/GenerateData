package zhangyu.fool.generate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zhangyu.fool.generate.util.NameConvertUtil;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class NameConvertUtilTest {


    /**
     * 测试名称转换为数据库规则
     */
    @Test
    public void convertToDataBaseRuleTest(){
        this.assertEquals("Course","course");
        this.assertEquals("CourseRecord","course_record");
        this.assertEquals("CourseRecordTest","course_record_test");
        this.assertEquals("userId","user_id");
        this.assertEquals("courseRecordId","course_record_id");
    }

    private void assertEquals(String originalStr, String expectStr){
        String resultStr1 = NameConvertUtil.convertToDataBaseRule(originalStr);
        Assertions.assertEquals(expectStr,resultStr1,"转换结果与期望结果不相等");
    }


}
