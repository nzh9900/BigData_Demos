package com.ni.algorithm;

import java.util.Stack;

/**
 * @ClassName AlgorithmStack
 * @Description 栈
 * @Author zihao.ni
 * @Date 2023/6/25 21:18
 * @Version 1.0
 **/
public class AlgorithmStack {
    public static void main(String[] args) {
        test();
    }

    // 栈的常用操作
    static void test() {
        Stack<String> stack = new Stack<>();
        stack.push("foo");
        stack.push("bar");
        stack.push("baz");

        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
    }
}