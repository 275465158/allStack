package com.ecms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveLog {

    /**
     * 需要记录日志的名称
     * 默认为空，则取类名+方法名
     * @return
     */
    String name() default "";
}
