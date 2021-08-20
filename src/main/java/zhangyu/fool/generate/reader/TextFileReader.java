package zhangyu.fool.generate.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
            lines.forEach(line -> {
                String[] split = line.split(",");
                for (String str : split) {
                    if (!"".equals(str.trim())) {
                        wordList.add(str.trim());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordList;
    }

}
