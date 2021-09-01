package zhangyu.fool.generate.service.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author xiaomingzhang
 * @date 2021/8/20
 */
public class ReadWriterTest {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\asus\\Desktop\\test\\新建文本文档.txt";
        String destPath = "C:\\Users\\asus\\Desktop\\test\\school.txt";
        try(FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath)))) {
            Stream<String> lines = bufferedReader.lines();

            List<String> strList = new ArrayList<>(16);
            lines.forEach(line -> {
                    String[] split = line.split("\t");
                    for (String str : split){
                        if(!"".equals(str.trim())){
                            strList.add(str.trim());
                        }
                    }
            });
            for (int i = 0; i < strList.size(); i++){
                String str = strList.get(i) + ",";
                if(i % 5 == 0 && i != 0){
                    str = str + "\n";
                }
                bufferedWriter.write(str);
            }
            bufferedWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
