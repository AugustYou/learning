package top.felixu.v3.plugin;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author felixu
 * @datetime 2018/4/12 16:33
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Signature {
  Class<?> type();

  String method();

  Class<?>[] args();
}