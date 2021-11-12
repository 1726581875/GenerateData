package zhangyu.fool.generate.service.random.factory;

import org.reflections.Reflections;
import zhangyu.fool.generate.MainRunner;
import zhangyu.fool.generate.annotation.BindType;
import zhangyu.fool.generate.service.random.FoolRandom;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaomingzhang
 * @date 2021/8/19
 */
public class RandomFactory {

    private static final Map<Class<?>, FoolRandom> BEAN_MAP = new ConcurrentHashMap<>();

    private static final Map<Class<?>, FoolRandom> MAPPING_MAP = new HashMap<>(16);

    /**
     * 初始化random工厂实例
     */
    static {
        //反射获取对应包标有带@BindType注解的类
        Reflections reflections = new Reflections(MainRunner.class.getPackage().getName());
        Set<Class<?>> randomClassSet = reflections.getTypesAnnotatedWith(BindType.class);
        for (Class<?> randomClass : randomClassSet) {
            //获取本类标注的注解，解析value获取要处理的类型
            BindType annotation = randomClass.getDeclaredAnnotation(BindType.class);
            if (annotation != null) {
                try {
                    FoolRandom instance = (FoolRandom) randomClass.newInstance();
                    Class<?>[] handleTypeClassArr = annotation.value();
                    for (Class<?> handleTypeClass : handleTypeClassArr) {
                        MAPPING_MAP.put(handleTypeClass, instance);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取一个random
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends FoolRandom> T get(Class<T> clazz) {
        if (BEAN_MAP.get(clazz) == null) {
            try {
                Constructor<T> constructor = clazz.getConstructor(null);
                T instance = constructor.newInstance(null);
                BEAN_MAP.put(clazz, instance);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return (T) BEAN_MAP.get(clazz);
    }

    /**
     * 根据java包装类型获取对应的random类
     *
     * @param typeClass
     * @param
     * @return
     */
    public static FoolRandom getRandomByType(Class<?> typeClass) {
        if (typeClass.isPrimitive()) {
            //typeClass = getPackageType(typeClass);
        }
        return MAPPING_MAP.get(typeClass);
    }

    /**
     * todo 暂时做适配
     * 获取基本类型Class对应的包装类型Class
     *
     * @param primitiveType
     * @return
     */
    private static Class<?> getPackageType(Class<?> primitiveType) {
        if (primitiveType.equals(Integer.TYPE)) {
            return Integer.class;
        } else if (primitiveType.equals(Long.TYPE)) {
            return Long.class;
        } else if (primitiveType.equals(Short.TYPE)) {
            return Short.class;
        } else if (primitiveType.equals(Boolean.TYPE)) {
            return Boolean.class;
        } else if (primitiveType.equals(Double.TYPE)) {
            return Double.class;
        } else if (primitiveType.equals(Character.TYPE)) {
            return Character.class;
        } else if (primitiveType.equals(Byte.TYPE)) {
            return Byte.class;
        } else {
            return primitiveType;
        }
    }

    /**
     * 根据Field获取对应随机值
     *
     * @param field
     * @return
     */
    public static Object getRandomValueType(Field field) {
        FoolRandom random = getRandomByType(field.getType());
        if (random == null) {
            throw new RuntimeException("获取不到类型" + field.getType().getName() + "对应random类");
        }
        return random.randomValue(field);
    }

}
