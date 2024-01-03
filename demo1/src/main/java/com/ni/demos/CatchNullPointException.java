package com.ni.demos;

/**
 * @ClassName CatchNullPointException
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/3 17:13
 * @Version 1.0
 **/
public class CatchNullPointException {
    public static void main(String[] args) {
        doCatch();
        System.out.println("===========================");
        doNotCatch();
        System.out.println("---------------------------");
    }


    public static void doCatch() {
        String a = null;
        try {
            System.out.println(a.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("catch success");
    }

    public static void doNotCatch() {
        String a = null;
        System.out.println(a.length());
        System.out.println("catch success");
    }
}