package com.ni.utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Random;

/**
 * @ClassName RandomUtils
 * @Description 随机数
 * @Author zihao.ni
 * @Date 2023/6/21 14:36
 * @Version 1.0
 **/
public class RandomUtils {
    private static final Random random = new Random();

    public static int getInt(int x, int y) {
        if (x < 0 || y < 0 || x > y) {
            return 0;
        }

        return (int) (random.nextFloat() * (y - x + 1)) + x;
    }

    /**
     * 随机获取列表中的元素
     */
    public static Object randomAccessList(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(RandomUtils.getInt(0, list.size() - 1));
    }
}