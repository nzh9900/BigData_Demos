package com.ni.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName Combination
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/6/26 15:58
 * @Version 1.0
 **/
public class Combination {
    public static void main(String[] args) {
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        Object[] objects = {'a', 'b', 'c', 'd', 'e'};
        for (int i = 1; i <= chars.length; i++) {
            //generateCombinations(chars, i, "");
            generateStrings(Arrays.asList(objects), i, new ArrayList<>());
        }
    }

    private static void generateStrings(List<Object> objects, int length, List<Object> current) {
        if (current.size() == length) {
            System.out.println(current);
        } else {
            for (int i = 0; i < objects.size(); i++) {
                if (current.contains(objects.get(i))) {
                    continue;
                }
                ArrayList<Object> middleObject = new ArrayList<>(current);
                middleObject.add(objects.get(i));
                generateStrings(objects, length, middleObject);
            }
        }
    }


    private static void generateCombinations(char[] chars, int length, String current) {
        if (current.length() == length) {
            System.out.println(current);
        } else {
            for (int i = 0; i < chars.length; i++) {
                if (current.indexOf(chars[i]) != -1) {
                    continue;
                }
                generateCombinations(chars, length, current + chars[i]);
            }
        }
    }
}