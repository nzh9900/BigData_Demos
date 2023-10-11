package com.ni.test;

import com.ni.annotation.MyAnnotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName Test01
 * @Description 获取注解上的内容
 * @Author zihao.ni
 * @Date 2023/10/11 08:08
 * @Version 1.0
 **/
public class Test01 {
    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
        getAnnotationDetailFromClass();
        getAnnotationDetailFromField();
        getAnnotationDetailFromMethod();
    }

    private static void getAnnotationDetailFromField() throws NoSuchFieldException {
        InnerDemo innerDemo = new InnerDemo();
        Field code = innerDemo.getClass().getDeclaredField("code");
        if (code.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = code.getAnnotation(MyAnnotation.class);
            System.out.println("from field: " + annotation.code());
            System.out.println("from field: " + annotation.desc());
            System.out.println("from field: " + annotation.clazz());
        }
    }

    private static void getAnnotationDetailFromClass() {
        MyAnnotation annotation = InnerDemo.class.getAnnotation(MyAnnotation.class);
        System.out.println("from class: " + annotation.code());
        System.out.println("from class: " + annotation.desc());
        System.out.println("from class: " + annotation.clazz());
    }

    private static void getAnnotationDetailFromMethod() throws NoSuchMethodException {
        Method test = InnerDemo.class.getDeclaredMethod("test");
        if (test.isAnnotationPresent(MyAnnotation.class)) {
            MyAnnotation annotation = test.getDeclaredAnnotation(MyAnnotation.class);
            System.out.println("from method: " + annotation.code());
            System.out.println("from method: " + annotation.desc());
            System.out.println("from method: " + annotation.clazz());
        }
    }
}

@MyAnnotation(code = 0, desc = "on class", clazz = Test01.class)
class InnerDemo {
    @MyAnnotation(code = 1, desc = "on field", clazz = Test01.class)
    private Integer code;

    @MyAnnotation(code = 2, desc = "on method", clazz = InnerDemo.class)
    private void test() {
        System.out.println("aaa");
    }
}