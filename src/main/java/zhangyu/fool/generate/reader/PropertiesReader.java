package zhangyu.fool.generate.reader;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author xiaomingzhang
 * @date 2021/8/30
 * 读取properties文件
 */
public class PropertiesReader {

    private static final Logger log = LoggerFactory.getLogger(PropertiesReader.class);

    private static final Properties properties = new Properties();

    static {
        InputStream inputStream = Object.class.getResourceAsStream("/config.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("加载配置文件config.properties发生异常", e);
        }
    }


    public static String get(String key){
        return properties.getProperty(key);
    }


}
