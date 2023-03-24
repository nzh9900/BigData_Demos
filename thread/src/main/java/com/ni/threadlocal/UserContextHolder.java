package com.ni.threadlocal;

/**
 * @ClassName UserContextHolder
 * @Description
 * @Author zihao.ni
 * @Date 2023/3/24 16:29
 * @Version 1.0
 **/
public class UserContextHolder {
    private static final ThreadLocal<String> USER_CONTEXT = new ThreadLocal<>();

    public static void set(String user) {
        USER_CONTEXT.set(user);
    }

    public static String get() {
        if (USER_CONTEXT.get() == null) {
            return "null";
        }
        return USER_CONTEXT.get();
    }

    public static void remove() {
        if (USER_CONTEXT.get() != null) {
            USER_CONTEXT.remove();
        }
    }
}