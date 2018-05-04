package top.felixu.framework.web;

import java.util.Map;

/**
 * @Author felixu
 * @Date 2018/5/3
 */
public class ModelAndView {

    private String viewName;
    private Map<String, ?> model;

    public ModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}