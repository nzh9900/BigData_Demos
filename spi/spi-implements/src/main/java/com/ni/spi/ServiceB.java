package com.ni.spi;

/**
 * @ClassName ServiceB
 * @Description 实现类B
 * @Author zihao.ni
 * @Date 2023/4/7 10:19
 * @Version 1.0
 **/
public class ServiceB implements Service {
    @Override
    public void print() {
        System.out.println("ServiceB");
    }
}