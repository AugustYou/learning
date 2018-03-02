package top.felixu.my;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author : felixu
 * @createTime : 2018/3/1.
 */
public class MyClassLoader extends ClassLoader {

    private File baseDir;

    public MyClassLoader() {
        String path = MyClassLoader.class.getResource("").getPath();
        this.baseDir = new File(path);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = MyClassLoader.class.getPackage().getName() + "." + name;
        if (baseDir != null) {
            File classFile = new File(baseDir, name.replace("\\.", "/") + ".class");
            if (classFile.exists()) {
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try {
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = in.read(buff)) != -1) {
                        out.write(buff, 0, len);
                    }
                    return defineClass(className, out.toByteArray(), 0, out.size());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != in) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != out) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    classFile.delete();
                }
            }
        }
        return null;
    }
}
