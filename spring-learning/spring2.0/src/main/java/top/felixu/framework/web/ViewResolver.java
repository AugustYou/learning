package top.felixu.framework.web;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设计这个类的目的是：
 * 1. 将一个静态文件变成一个动态文件
 * 2. 根据用户传送的参数不同，产生不同的结果
 * 最终输出字符串，交给Response
 * @Author felixu
 * @Date 2018/5/3
 */
public class ViewResolver {

    private String viewName;
    private File template;
    private Pattern pattern = Pattern.compile("#\\{(.+?)}", Pattern.CASE_INSENSITIVE);

    public ViewResolver(String viewName, File template) {
        this.viewName = viewName;
        this.template = template;
    }

    public String viewResolver(ModelAndView mv) throws Exception {
        StringBuffer sb = new StringBuffer();
        RandomAccessFile ra = new RandomAccessFile(this.template, "r");
        String line = null;
        while (null != (line = ra.readLine())) {
            line = new String(line.getBytes("ISO-8859-1"), "utf-8");
            Matcher matcher = matcher(line);
            while (matcher.find()) {
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String paramName = matcher.group(i);
                    Object paramValue = mv.getModel().get(paramName);
                    if (null == paramName) {continue;}
                    line = line.replaceAll("#\\{" + paramName + "}", paramValue.toString());
                    line = new String(line.getBytes("utf-8"), "ISO-8859-1");
                }
            }
            sb.append(line);
        }
        return sb.toString();
    }

    private Matcher matcher(String str) {
        Matcher matcher = this.pattern.matcher(str);
        return matcher;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public File getTemplate() {
        return template;
    }

    public void setTemplate(File template) {
        this.template = template;
    }
}