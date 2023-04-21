package com.ni.test;


import java.io.UnsupportedEncodingException;

/**
 * @ClassName Test01
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/4/20 16:42
 * @Version 1.0
 **/
public class Test01 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "���������������������";
        String s = new String(a.getBytes(), "UTF-8");
        System.out.println(s);
    }
}