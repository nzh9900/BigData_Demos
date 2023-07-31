package com.ni.demos;

import java.util.HashSet;

/**
 * @ClassName CloneTest
 * @Description
 * @Author zihao.ni
 * @Date 2023/7/31 11:32
 * @Version 1.0
 **/
public class CloneTest {
    public static void main(String[] args) {
        testCloneSet();
    }

    private static void testCloneSet() {
        HashSet<Long> longs = new HashSet<>();
        longs.add(10L);
        longs.add(12L);
        longs.add(13L);
        longs.add(15L);

        HashSet<Long> clone = (HashSet<Long>) longs.clone();

        System.out.println("init:" + longs);
        System.out.println("init:" + clone);

        longs.remove(10L);
        System.out.println("-10:" + longs);
        System.out.println("-10:" + clone);

    }
}