package com.ni.spi;

/**
 * @ClassName serviceA
 * @Description 实现类A
 * @Author zihao.ni
 * @Date 2023/4/7 10:19
 * @Version 1.0
 **/
public class ServiceA implements Service {

    @Override
    public void print() {
        System.out.println("ServiceA");
    }
}