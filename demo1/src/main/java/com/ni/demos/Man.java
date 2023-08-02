package com.ni.demos;

/**
 * @ClassName Man
 * @Description
 * @Author zihao.ni
 * @Date 2023/8/2 15:32
 * @Version 1.0
 **/
public class Man implements ManInterface {
    @Override
    public void print() {
        System.out.println("Man print");
    }

    public static void main(String[] args) {
        ManInterface man = new Man();
        man.print();
    }

}