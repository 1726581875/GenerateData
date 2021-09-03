package zhangyu.fool.generate.service.builder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import zhangyu.fool.generate.object.test.mysql.FoolDatabase;
import zhangyu.fool.generate.service.random.factory.RandomFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * @author xiaomingzhang
 * @date 2021/9/1
 */
public class ElasticsearchDataBuilder {


    public String buildJsonObjectArray(Class<?> entityClass, int rowNum){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        List<Object> objectList = new ArrayList<>(rowNum);
        for (int i = 0; i < rowNum; i++) {
            try {
                Object instance = entityClass.newInstance();
                Field[] fields = entityClass.getDeclaredFields();
                for (Field field : fields) {
                    Object value = RandomFactory.getRandomValueType(field);
                    field.setAccessible(true);
                    field.set(instance, value);
                }
                objectList.add(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return gson.toJson(objectList);
    }

    public static void main(String[] args) {
        ElasticsearchDataBuilder dataBuilder = new ElasticsearchDataBuilder();
        System.out.println(dataBuilder.buildJsonObjectArray(FoolDatabase.class, 2));
    }


}
