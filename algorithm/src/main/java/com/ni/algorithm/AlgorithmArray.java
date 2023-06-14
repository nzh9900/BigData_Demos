package com.ni.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName Algorithm_Array
 * @Description
 * @Author zihao.ni
 * @Date 2023/6/14 16:48
 * @Version 1.0
 **/
public class AlgorithmArray {
    private static final Random random = new Random();

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 3; i++) {
            System.out.println("随机返回一个数组元素:" + randomAccess(nums));
        }

        System.out.println("扩展数组长度+2,原始长度:" + nums.length + ",扩展后长度:" + extend(nums, 2).length);

        Arrays.stream(insert(nums, 1, 3)).forEach(element -> System.out.print(element + ","));
        System.out.println();

        Arrays.stream(remove(nums, 1)).forEach(element -> System.out.print(element + ","));

        //https://www.hello-algo.com/chapter_array_and_linkedlist/array/#413
    }


    /**
     * 随机返回一个数组元素
     *
     * @param nums
     */
    static int randomAccess(int[] nums) {

        int i = random.nextInt(nums.length);
        return nums[i];

    }

    /**
     * 扩展数组长度
     */
    static int[] extend(int[] nums, int enlarge) {
        int[] largeNums = new int[nums.length + enlarge];
        for (int i = 0; i < nums.length; i++) {
            largeNums[i] = nums[i];
        }

        return largeNums;
    }

    /**
     * 在数组的索引 index 处插入元素 num
     */
    static int[] insert(int[] nums, int num, int index) {
        int[] extendNums = extend(nums, 1);
        for (int i = extendNums.length - 1; i > index; i--) {
            extendNums[i] = extendNums[i - 1];
        }
        extendNums[index] = num;

        return extendNums;
    }

    /**
     * 删除索引 index 处元素
     */
    static int[] remove(int[] nums, int index) {
        for (int i = index; i < nums.length - 1; i++) {
            nums[i] = nums[i + 1];
        }
        return nums;
    }
}