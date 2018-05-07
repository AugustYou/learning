package top.felixu.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * 处理器适配器
 * @Author felixu
 * @Date 2018/5/3
 */
public class HandlerAdapter {

    private Map<String, Integer> paramMapping;

    public HandlerAdapter(Map<String, Integer> paramMapping) {
        this.paramMapping = paramMapping;
    }

    /**
     * 传入req和resp是为了传参，也就是为什么在Controller中的方法上可以拿到req和resp
     * @param req
     * @param resp
     * @param handler 传入handler是因为handler中有url，Controller，Method
     * @return
     */
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, HandlerMapping handler) throws Exception {
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        Map<String, String[]> parameterMap = req.getParameterMap();
        Object[] paramValues = new Object[parameterTypes.length];
        parameterMap.forEach((key, value) -> {
            String param = Arrays.toString(value).replaceAll("\\[|\\]", "").replaceAll("\\s", "");
            if (this.paramMapping.containsKey(key)) {
                int index = this.paramMapping.get(key);
                paramValues[index] = caseStringValue(param, parameterTypes[index]);
            }
        });
        if (this.paramMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = this.paramMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }
        if (this.paramMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = this.paramMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }
        Object result = handler.getMethod().invoke(handler.getController(), paramValues);
        return Optional.ofNullable(result)
                .map(r -> {
                    if (handler.getMethod().getReturnType() == ModelAndView.class) {
                        return (ModelAndView) r;
                    }
                    return null;
                })
                .orElse(null);
    }

    private Object caseStringValue(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf(value);
        } else {
            return null;
        }
    }
}