package top.felixu.v1;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author felixu
 * @datetime 2018/4/1 21:05
 */
public class Configuration {
    public <T> T getMapper(Class<T> clazz, SqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                new Class[]{clazz}, new MapperProxy(sqlSession));
    }

    /**
     * hardcode代替完成xml解析
     */
    static class PersonMapperXml {
        public static final String NAME_SPACE = "top.felixu.v1.PersonMapper";

        public static final Map<String, String> SQL_MAPPING = new HashMap<>();

        static {
            SQL_MAPPING.put("selectByPrimaryKey", "SELECT * FROM person WHERE id = ?");
        }
    }
}
