package com.ni.utils;

import java.util.ArrayList;
import java.util.List;

public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("a");
        some(list);
    }

    public static void some(List<String> list){
        System.out.println(list);
    }
}
