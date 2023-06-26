package com.ni.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * @ClassName AlgorithmList
 * @Description 列表
 * @Author zihao.ni
 * @Date 2023/6/15 11:21
 * @Version 1.0
 **/
public class AlgorithmList {
    // 列表基于数组实现，继承了数组的优点，并且可以在程序运行过程中动态扩容。
    // 在列表中，我们可以自由添加元素，而无需担心超过容量限制。

    // 初始化列表
    static void init() {
        ArrayList<Integer> emptyList = new ArrayList<>();

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
    }

    // 访问与更新元素
    static void getAndUpdate() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        list.get(2);
        list.set(2, 10);
    }

    // 在列表中添加、插入、删除元素
    static void appendAddDelete() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        // append
        list.add(10);
        // add
        list.add(1, 20);
        // delete
        list.remove(0);
    }

    // 遍历列表
    static void foreach() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        for (Integer e : list) {
            System.out.println(e);
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    // 拼接两个列表
    static void addAll() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(4, 5, 6));
        list.addAll(list2);
        System.out.println(list);
    }

    // 排序列表
    static void sort() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        Collections.sort(list);
    }

}

// 列表实现
