package com.ni;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LocalThreadTest
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/3/24 09:14
 * @Version 1.0
 **/
public class LocalThreadTest {
    private static final ThreadLocal<String> localVar = new ThreadLocal<>();

    private static final List<String> list = new ArrayList<>();

    public void testLocalThread() throws InterruptedException {
        new Thread(() -> {
            localVar.set("cc");
            System.out.println("A线程里面" + localVar.get());
        }).start();

        new Thread(() -> {
            localVar.set("jj");
            System.out.println("B线程里面" + localVar.get());
        }).start();


        Thread.sleep(1000);

        System.out.println(localVar.get());
    }

    public void testMemberProfile() throws InterruptedException {

        new Thread(() -> {
            list.add("cc");
            System.out.println("A线程里面" + list);
        }).start();

        new Thread(() -> {
            list.add("jj");
            System.out.println("B线程里面" + list);
        }).start();

        Thread.sleep(1000);
        System.out.println(list);

    }


    public static void main(String[] args) throws InterruptedException {
        LocalThreadTest localThreadTest = new LocalThreadTest();
        //localThreadTest.testLocalThread();

        localThreadTest.testMemberProfile();
    }
}