package zhangyu.fool.generate.service.random.string;

import zhangyu.fool.generate.annotation.BindRole;
import zhangyu.fool.generate.enums.RuleType;
import zhangyu.fool.generate.service.reader.TextFileReader;

import java.util.List;

/**
 * @author xmz
 * @date: 2021/08/22
 */
@BindRole(RuleType.NAME)
public class SchoolRandom extends StringRandom implements RuleStringRandom{


    private static List<String> schoolList = null;

    private static final String SCHOOL_FILE_PATH = "src/main/resources/school.txt";

    @Override
    public String randomRuleString() {
        checkAndInit();
        return schoolList.get(getRandomInt(0, schoolList.size() - 1));
    }

    public void checkAndInit() {
        //double check
        if (schoolList == null) {
            synchronized (NameRandom.class) {
                if (schoolList == null) {
                    TextFileReader textFileReader = new TextFileReader();
                    schoolList = textFileReader.readWord(SCHOOL_FILE_PATH);
                }
            }
        }
    }


}
