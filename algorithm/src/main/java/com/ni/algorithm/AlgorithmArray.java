package com.ni.algorithm;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName Algorithm_Array
 * @Description 数组
 * @Author zihao.ni
 * @Date 2023/6/14 16:48
 * @Version 1.0
 **/
public class AlgorithmArray {
    // 「数组 Array」是一种线性数据结构，其将相同类型元素存储在连续的内存空间中。我们将元素在数组中的位置称为元素的「索引 Index」。
    private static final Random random = new Random();

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 3; i++) {
            System.out.println("随机返回一个数组元素:" + randomAccess(nums));
        }

        System.out.println("扩展数组长度+2,原始长度:" + nums.length + ",扩展后长度:" + extend(Arrays.copyOf(nums, 10), 2).length);

        System.out.print("替换数组index为3的元素值为1: ");
        Arrays.stream(insert(Arrays.copyOf(nums, 10), 1, 3)).forEach(element -> System.out.print(element + ","));
        System.out.println();

        System.out.print("删除数组index为1的元素: ");
        Arrays.stream(remove(Arrays.copyOf(nums, 10), 1)).forEach(element -> System.out.print(element + ","));
        System.out.println();

        int target = 2;
        System.out.println("查找数组元素: " + target + ",索引位置:" + find(nums, target));


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

    /**
     * 在数组中查找指定元素
     */
    static int find(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }
}