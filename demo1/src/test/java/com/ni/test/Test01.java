package com.ni.test;


import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName Test01
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/4/20 16:42
 * @Version 1.0
 **/
public class Test01 {
    @Test
    public void testA() throws UnsupportedEncodingException {
        String a = "���������������������";
        String s = new String(a.getBytes(), "UTF-8");
        System.out.println(s);
    }

    @Test
    public void testB() {
        RuntimeException runtimeException = new RuntimeException();
        System.out.println(runtimeException.getMessage());
        System.out.println("====================");
        runtimeException.printStackTrace();
        System.out.println("====================");
        System.out.println(runtimeException.getLocalizedMessage());
    }
}