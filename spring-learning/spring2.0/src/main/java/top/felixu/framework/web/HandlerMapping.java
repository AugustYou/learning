package top.felixu.framework.web;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 保存url和对应Controller中Method的对应关系
 * url本身支持通配符，则应该是正则Pattern
 * @Author felixu
 * @Date 2018/5/3
 */
public class HandlerMapping {

    private Object controller;
    private Method method;
    private Pattern url;

    public HandlerMapping(Object controller, Method method, Pattern url) {
        this.controller = controller;
        this.method = method;
        this.url = url;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getUrl() {
        return url;
    }

    public void setUrl(Pattern url) {
        this.url = url;
    }
}