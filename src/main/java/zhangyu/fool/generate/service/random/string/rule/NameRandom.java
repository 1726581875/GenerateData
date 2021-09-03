package zhangyu.fool.generate.service.random.string.rule;

import zhangyu.fool.generate.annotation.BindRole;
import zhangyu.fool.generate.enums.RuleType;
import zhangyu.fool.generate.service.random.string.StringRandom;
import zhangyu.fool.generate.service.reader.TextFileReader;

import java.util.List;

/**
 * @author xiaomingzhang
 * @date 2021/8/20
 */
@BindRole(RuleType.NAME)
public class NameRandom extends StringRandom implements RuleStringRandom {

    private static List<String> familyNameList = null;

    private static List<String> nameList = null;

    private static final String FAMILY_NAME_FILE_PATH = "src/main/resources/姓.txt";

    private static final String NAME_FILE_PATH = "src/main/resources/名.txt";

    private boolean useFaker = true;

    @Override
    public String randomRuleString() {
        return randomValue();
    }

    @Override
    public String randomValue() {
        //使用faker生成人名
        if(useFaker){
          return FAKER.name().fullName();
        }
        //自己生成名字
        checkAndInit();
        String familyName = getFamilyName();
        String name = getName();
        return familyName + name;
    }


    private String getFamilyName() {
        int index = getRandomInt(0, familyNameList.size() - 1);
        return familyNameList.get(index);
    }

    private String getName() {
        //名字随机一个字或者两个字
        int nameCharNum = getRandomInt(1, 2);
        String name = "";
        for (int i = 0; i < nameCharNum; i++) {
            int index = getRandomInt(0, nameList.size() - 1);
            name += nameList.get(index);
        }
        return name;
    }

    public void checkAndInit() {
        //double check
        if (isNotInit()) {
            synchronized (NameRandom.class) {
                if (isNotInit()) {
                    initNameList();
                }
            }
        }
    }

    private boolean isNotInit(){
        return (familyNameList == null || nameList == null);
    }


    private void initNameList() {
        TextFileReader textFileReader = new TextFileReader();
        familyNameList = textFileReader.readWord(FAMILY_NAME_FILE_PATH);
        nameList = textFileReader.readWord(NAME_FILE_PATH);
    }

}
