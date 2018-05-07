package top.felixu.framework.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
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

    public class Aspect {

        private Object aspect;
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
