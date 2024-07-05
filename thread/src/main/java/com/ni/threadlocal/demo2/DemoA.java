package com.ni.threadlocal.demo2;

/**
 * @ClassName DemoA
 * @Description
 * @Author zihao.ni
 * @Date 2024/6/27 14:55
 * @Version 1.0
 **/
public class DemoA {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("start");
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("end");
        });

        thread.start();
        // 使用join 使main线程等待thread线程结束
        thread.join(2000);

        System.out.println("main end");
    }
}