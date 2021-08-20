package zhangyu.fool.generate.reader;

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
        String filePath = "C:\\Users\\admin\\Desktop\\百家姓\\名.txt";
        String destPath = "C:\\Users\\admin\\Desktop\\百家姓\\bbb.txt";
        try(FileInputStream fileInputStream = new FileInputStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath)))) {
            Stream<String> lines = bufferedReader.lines();

            List<String> strList = new ArrayList<>(16);
            lines.forEach(line -> {
                    String[] split = line.split("、");
                    for (String str : split){
                        if(!"".equals(str.trim())){
                            strList.add(str.trim());
                        }
                    }
            });
            for (int i = 0; i < strList.size(); i++){
                String str = strList.get(i) + ",";
                if(i % 10 == 0 && i != 0){
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
