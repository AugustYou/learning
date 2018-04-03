package top.felixu.v2.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author felixu
 * @datetime 2018/4/2 22:37
 */
public class MapperRegistry {
    private Map<String, Map<String, String>> sqlMapping  = new HashMap<>();

    public void  putObject(String className, Map<String, String> methodSql) {
        sqlMapping.put(className, methodSql);
    }

    public Map<String, String> getObject(String className) {
        return sqlMapping.get(className);
    }
}
