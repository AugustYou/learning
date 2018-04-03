package top.felixu.v2.handler;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;

/**
 * @author felixu
 * @datetime 2018/4/3 06:53
 */
public class ResultSetHandler {

    public <T> T handler(ResultSet resultSet, Class returnType) {
        Object resultEntity = new DefaultObjectFactory().create(returnType);
        try {
            if (resultSet.next()) {
                Arrays.stream(resultEntity.getClass().getDeclaredFields())
                        .forEach(field -> fillColumn(resultEntity, field, resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (T) resultEntity;
    }

    private void fillColumn(Object resultEntity, Field column, ResultSet resultSet) {
        try {
            Method method = resultEntity.getClass().getMethod("set" + upperCase(column.getName().toCharArray()), column.getType());
            method.invoke(resultEntity, getValue(resultSet, column));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String upperCase(char[] chars) {
        if (97 <= chars[0] && chars[0] <= 122) {
            chars[0] ^= 32;
        }
        return new String(chars);
    }

    private Object getValue(ResultSet resultSet, Field field) throws SQLException {
        Class<?> type = field.getType();
        String fieldName = field.getName();
        if (Integer.class == type) {
            return resultSet.getInt(fieldName);
        } else if (String.class == type) {
            return resultSet.getString(fieldName);
        }
        return null;
    }
}
