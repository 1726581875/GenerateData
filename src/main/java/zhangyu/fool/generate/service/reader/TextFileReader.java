package zhangyu.fool.generate.service.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xiaomingzhang
 * @date 2021/8/20
 */
public class TextFileReader {


    public List<String> readWord(String filePath) {
        List<String> wordList = new ArrayList<>(16);
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            Stream<String> lines = bufferedReader.lines();
            String text = lines.collect(Collectors.joining(""));
            String[] split = text.split(",");
            for (String str : split) {
                if (!"".equals(str.trim())) {
                    wordList.add(str.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordList;
    }


    public static void main(String[] args) {
        TextFileReader textFileReader = new TextFileReader();
        List<String> tableNameListDev = textFileReader.readWord("C:\\Users\\xmz\\Desktop\\test2\\dev.txt");

        List<String> tableNameListSzDev = textFileReader.readWord("C:\\Users\\xmz\\Desktop\\test2\\sz_dev.txt");

        Set<String> devTableNameSet = new HashSet<>(tableNameListDev);

        for (String tableName : tableNameListSzDev){
            if(!devTableNameSet.contains(tableName)){
                System.out.println(tableName);
            }
        }




      //  tableNameListDev.forEach(System.out::println);


    }

}
