package com.ni.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName MyAnnotation
 * @Description 自定义注解
 * @Author zihao.ni
 * @Date 2023/10/11 07:41
 * @Version 1.0
 **/

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    int code();

    String desc();

    Class<?> clazz();
}