package top.felixu.framework.web.servlet;

import top.felixu.framework.annotation.Controller;
import top.felixu.framework.annotation.RequestMapping;
import top.felixu.framework.annotation.RequestParam;
import top.felixu.framework.context.ApplicationContext;
import top.felixu.framework.web.HandlerAdapter;
import top.felixu.framework.web.HandlerMapping;
import top.felixu.framework.web.ModelAndView;
import top.felixu.framework.web.ViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author felixu
 * @Date 2018/4/24
 */
public class DispatchServlet extends HttpServlet {

    private final String LOCATION = "contextConfigLocation";

    private final List<HandlerMapping> handlerMappings = new ArrayList<>();

    private final Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<>();

    private final List<ViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * Spring中是在请求阶段先调用doService方法，在doService方法中调用了doDispatch，这里忽略细节
         */
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("<h3><strong>500 server error</strong></h3>" + "\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", "\r\n"));
            e.printStackTrace();
        }

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 根据用户请求获得一个handler
        HandlerMapping handler = getHandler(req);
        if (null == handler) {
            resp.getWriter().write("404 Not Found");
            return;
        }
        HandlerAdapter ha = getHandlerAdapter(handler);
        ModelAndView mv = ha.handle(req, resp, handler);
        processDispatchResult(resp, mv);
    }

    private void processDispatchResult(HttpServletResponse resp, ModelAndView mv) throws Exception {
        if (null == mv) {return;}
        if (this.viewResolvers.isEmpty()) {return;}
        for (ViewResolver viewResolver : this.viewResolvers) {
            if (mv.getViewName().equals(viewResolver.getViewName())) {
                String out = viewResolver.viewResolver(mv);
                if (out != null) {
                    resp.getWriter().write(out);
                    break;
                }
            }
        }
    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {
        if (this.handlerAdapters.isEmpty()) {
            return null;
        }
        return this.handlerAdapters.get(handler);
    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if (!this.handlerMappings.isEmpty()) {
            String url = req.getRequestURI();
            String contextPath = req.getContextPath();
            url = url.replace(contextPath, "").replaceAll("/+", "/");
//            String finalUrl = url;
//            return this.handlerMappings.stream().filter(handlerMapping -> handlerMapping.getUrl().matcher(finalUrl).matches()).findAny().orElseGet(null);
            for (HandlerMapping handlerMapping : this.handlerMappings) {
                Matcher matcher = handlerMapping.getUrl().matcher(url);
                if (matcher.matches()) {
                    return handlerMapping;
                }
            }
        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        /**
         * 相当于把IOC容器初始化了
         */
        ApplicationContext context = new ApplicationContext(config.getInitParameter(LOCATION));

        /**
         * 在Spring中是由ApplicationContextAware的OnRefresh方法来初始化的，这里简化在init中直接调用
         */
        initStrategies(context);
    }

    private void initStrategies(ApplicationContext context) {
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    /**
     * flash映射管理器
     * @param context
     */
    private void initFlashMapManager(ApplicationContext context) {

    }

    /**
     * 通过ViewResolver解析逻辑视图到具体视图实现
     * 实现动态模版的解析
     * 自己解析一套模版语音，类似freemark和velocity之类
     * @param context
     */
    private void initViewResolvers(ApplicationContext context) {
        String template = context.getConfig().getProperty("template");
        String path = Objects.requireNonNull(this.getClass().getClassLoader().getResource(template)).getFile();
        File dir = new File(path);
        Arrays.stream(dir.listFiles())
                .forEach(file -> {
                    this.viewResolvers.add(new ViewResolver(file.getName(), file));
                });
    }

    /**
     * 解析请求到视图名
     * @param context
     */
    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    /**
     * 异常解析
     * @param context
     */
    private void initHandlerExceptionResolvers(ApplicationContext context) {

    }

    /**
     * 通过HandlerAdapter进行多类型的参数动态匹配
     * 用来动态匹配Method参数，包括类型转换，动态赋值
     * @param context
     */
    private void initHandlerAdapters(ApplicationContext context) {
        /**
         * 在初始化阶段，将参数名称或者类型按顺序存储
         * 反射调用传入形参数组
         */
        this.handlerMappings.forEach(handlerMapping -> {
            // 每个方法有自己的形参列表
            Map<String, Integer> paramMapping = new HashMap<>(16);
            Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
            for (int i = 0; i < pa.length; i++) {
                int index = i;
                Arrays.stream(pa[i])
                        .filter(annotation -> annotation instanceof RequestParam)
                        .forEach(annotation -> {
                            String paramName = ((RequestParam) annotation).value();
                            if (!"".equals(paramName.trim())) {
                                paramMapping.put(paramName, index);
                            }
                        });
            }

            // 处理Request和Response
            Class<?>[] parameterTypes = handlerMapping.getMethod().getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> type = parameterTypes[i];
                if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                    paramMapping.put(type.getName(), i);
                }
            }
            this.handlerAdapters.put(handlerMapping, new HandlerAdapter(paramMapping));
        });
    }

    /**
     * 通过HandlerMapping将请求映射到处理器
     * 用来保存Controller中配置的RequestMapping和Method的一一对应关系
     * @param context
     */
    private void initHandlerMappings(ApplicationContext context) {
        /**
         * 初步想法应该是Map<String, Method>
         * 即map.put(url, Method)
         */
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.stream(beanNames)
                .forEach(beanName -> {
                    Object instance = context.getBean(beanName);
                    Class<?> clazz = instance.getClass();
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        String baseUrl = "";
                        if (clazz.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                            baseUrl = requestMapping.value();
                        }
                        // 扫描所有public方法
                        Method[] methods = clazz.getMethods();
                        for (Method method : methods) {
                            if (method.isAnnotationPresent(RequestMapping.class)) {
                                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                                String regex = ("/" + baseUrl + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                                Pattern pattern = Pattern.compile(regex);
                                this.handlerMappings.add(new HandlerMapping(instance, method, pattern));
                            }
                        }

                    }
                });
    }

    /**
     * 主题解析
     * @param context
     */
    private void initThemeResolver(ApplicationContext context) {

    }

    /**
     * 本地化解析
     * @param context
     */
    private void initLocaleResolver(ApplicationContext context) {

    }

    /**
     * 文件上传解析
     * @param context
     */
    private void initMultipartResolver(ApplicationContext context) {
    }
}
