package com.ni.demos;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ClassName DateFormat
 * @Description
 * @Author zihao.ni
 * @Date 2024/1/22 17:06
 * @Version 1.0
 **/
public class DateFormat {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(dateFormat.format(calendar.getTime()));

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        System.out.println(dateFormat.format(calendar.getTime()));
    }
}