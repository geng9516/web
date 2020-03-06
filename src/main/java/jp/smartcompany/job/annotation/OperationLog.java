package jp.smartcompany.job.annotation;

import java.lang.annotation.*;

/**
 * @author xiao wenpeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
  String value() default "";
}
