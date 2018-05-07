package top.felixu.framework.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 对application中的expression的封装
 * 目标代理对象的一个方法进行增强
 * 具体逻辑由自己实现的业务去增强
 * 配置文件中配置的目的：告诉Spring哪些类的哪些方法需要增强，增强的内容是什么
 * @Author felixu
 * @Date 2018/5/7
 */
public class AopConfig {

    private Map<Method, Aspect> points = new HashMap<>();

    public void put(Method target, Object aspect, Method[] points) {
        this.points.put(target, new Aspect(aspect, points));
    }

    public Aspect get(Method method) {
        return this.points.get(method);
    }

    public boolean contains(Method method) {
        return this.points.containsKey(method);
    }

    /**
     * 对增强对代码进行封装
     */
    public class Aspect {

        /**
         * 以LogAspect为例，会将其赋值给这个字段
         */
        private Object aspect;

        /**
         * 会将LogAspect对before方法和after方法赋值给这个字段
         */
        private Method[] points;

        public Aspect(Object aspect, Method[] points) {
            this.aspect = aspect;
            this.points = points;
        }

        public Object getAspect() {
            return aspect;
        }

        public Method[] getPoints() {
            return points;
        }
    }
}
