package com.ni.threadlocal.demo1;

/**
 * @ClassName ClientA
 * @Description
 * @Author zihao.ni
 * @Date 2023/3/24 16:38
 * @Version 1.0
 **/
public class ClientA {
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            new ClientA().client("aa");
        }).start();

        Thread.sleep(1000);

        new Thread(() -> {
            new ClientA().client("BB");
        }).start();

    }

    public void client(String user) {
        UserContextHolder.set(user);
        ClientA clientA = new ClientA();
        clientA.methodA();
        new TestA().getUser();
        new TestB().getUser();
        UserContextHolder.remove();
        new TestA().getUser();


    }

    public void methodA() {
        System.out.println("methodA " + UserContextHolder.get());
    }
}

class TestA {
    public void getUser() {
        System.out.println("TestA " + UserContextHolder.get());
    }
}

class TestB {
    public void getUser() {
        System.out.println("TestB " + UserContextHolder.get());
    }
}