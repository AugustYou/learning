package top.felixu.servlet;

import top.felixu.annotation.Autowired;
import top.felixu.annotation.Controller;
import top.felixu.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author felixu
 * @Date 2018/4/23
 */
public class DispatchServlet extends HttpServlet {

    private Properties contextConfig = new Properties();
    private Map<String, Object> ioc = new ConcurrentHashMap<>();
    private List<String> classNames = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        /**
         * 定位
         * 加载
         * 注册
         * 注入
         */
        // 定位
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 加载
        doScanner(contextConfig.getProperty("scan.package"));
        // 注册
        doRegistry();
        // 注入
        doAutowired();
        
        initHandlerMapping();
    }

    private void initHandlerMapping() {
    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        ioc.entrySet().stream()
                .forEach(entry -> {
                    Field[] fields = entry.getValue().getClass().getDeclaredFields();
                    Arrays.stream(fields)
                            .forEach(field -> {
                                if (field.isAnnotationPresent(Autowired.class)) {
                                    Autowired annotation = field.getAnnotation(Autowired.class);
                                    String beanName = annotation.value().trim();
                                    if ("".equals(beanName)) {
                                        beanName = field.getType().getName();
                                    }
                                    field.setAccessible(true);
                                    try {
                                        field.set(entry.getValue(), ioc.get(beanName));
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                });
    }

    private void doRegistry() {
        if (classNames.isEmpty()) {
            return;
        }
        classNames.stream().forEach(className -> {
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(Controller.class)) {
                    ioc.put(lowerFirstCase(clazz.getSimpleName()), clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    Service service = clazz.getAnnotation(Service.class);
                    String beanName = service.value().trim();
                    if ("".equals(beanName)) {
                        beanName = lowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    Class<?>[] interfaces = clazz.getInterfaces();
                    Arrays.stream(interfaces).forEach(i -> ioc.put(i.getName(), instance));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replace(".", "/"));
        File classDir = new File(url.getFile());
        Arrays.stream(classDir.listFiles())
                .forEach(file -> {
                    if (file.isDirectory()) {
                        doScanner(packageName + "." + file.getName());
                    } else {
                        classNames.add(packageName + "." + file.getName().replace(".class", ""));
                    }
                });
    }

    private void doLoadConfig(String location) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath:", ""));
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
