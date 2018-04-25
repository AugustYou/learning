package top.felixu.support;

import top.felixu.beans.BeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 对配置文件进行定位、读取、解析
 *
 * @Author felixu
 * @Date 2018/4/24
 */
public class BeanDefinitionReader {

    private Properties config = new Properties();
    private List<String> registryBeanNames = new ArrayList<>();
    private static final String SCAN_PACKAGE = "scan.package";

    public BeanDefinitionReader(String... locations) {
        Arrays.stream(locations)
                .forEach(location -> {
                    InputStream is = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath:", ""));
                    try {
                        this.config.load(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String packageName) {
        URL url = this.getClass().getClassLoader().getResource("/" + packageName.replace(".", "/"));
        File classDir = new File(url.getFile());
        Arrays.stream(classDir.listFiles())
                .forEach(file -> {
                    if (file.isDirectory()) {
                        doScanner(packageName + "." + file.getName());
                    } else {
                        registryBeanNames.add(packageName + "." + file.getName().replace(".class", ""));
                    }
                });
    }

    /**
     * Return configs
     * @return
     */
    public Properties getConfig() {
        return this.config;
    }

    public BeanDefinition registryBeanDefinition(String name) {
        if (registryBeanNames.contains(name)) {
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setBeanClassName(name);
            beanDefinition.setFactoryBeanName(lowerFirstCase(name.substring(name.lastIndexOf(".") + 1)));
            return beanDefinition;
        }
        return null;
    }

    public List<String> loadBeanDefinitions() {
        return this.registryBeanNames;
    }

    /**
     * Convert the first letter of a string to lowercase
     * @param str
     * @return
     */
    private String lowerFirstCase(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}