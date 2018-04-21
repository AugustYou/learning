package top.felixu.v3.plugin;

import java.lang.annotation.*;

/**
 * @author felixu
 * @datetime 2018/4/12 16:33
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
  Signature[] value();
}

