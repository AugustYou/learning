package top.felixu.my;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public class MyProxy {

    private static String ln = "\r\n";

    public static Object newProxyInstance(MyClassLoader loader, Class<?>[] interfaces, MyInvocationHandler h) {
        /**
         * 1.生成源代码
         * 2.将源代码输出到磁盘
         * 3.编译源代码生成class文件
         * 4.将class文件加载到jvm中
         * 5.返回被代理后的对象
         */
        try {
            // 1
            String proxySrc = generateSrc(interfaces[0]);

            // 2
            String path = MyProxy.class.getResource("").getPath();
            File file = new File(path + "$Proxy0.java");
            FileWriter writer = new FileWriter(file);
            writer.write(proxySrc);
            writer.flush();
            writer.close();

            // 3
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(file);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            // 4
            // 5
            Class<?> proxyClass = loader.findClass("$Proxy0");
            Constructor<?> constructor = proxyClass.getConstructor(MyInvocationHandler.class);
            file.delete();
            return constructor.newInstance(h);
//            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?> interfaces) {
        StringBuffer src = new StringBuffer();
        src.append("package top.felixu.my;" + ln);
        src.append(ln);
        src.append("import java.lang.reflect.Method;" + ln);
        src.append(ln);
        src.append("public class $Proxy0 implements " + interfaces.getName() + "{" + ln);
        src.append("MyInvocationHandler h;" + ln);
        src.append("public $Proxy0(MyInvocationHandler h) {" + ln);
        src.append("this.h = h;" + ln);
        src.append("}" + ln);
        for (Method method : interfaces.getMethods()) {
            src.append("public " + method.getReturnType().getName() + " " + method.getName() + "() {" + ln);
            src.append("try {" + ln);
            src.append("Method m = " + interfaces.getName() + ".class.getMethod(\"" + method.getName() + "\", new Class[]{});" + ln);
            src.append("this.h.invoke(this, m, null);" + ln);
            src.append("} catch(Throwable e) {" + ln);
            src.append("e.printStackTrace();" + ln);
            src.append("}" + ln);
            src.append("}" + ln);
        }
        src.append("}");
        return src.toString();
    }
}
