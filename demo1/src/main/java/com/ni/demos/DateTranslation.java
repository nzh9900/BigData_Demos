package com.ni.demos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName DateTranslation
 * @Description
 * @Author zihao.ni
 * @Date 2023/6/8 16:37
 * @Version 1.0
 **/
public class DateTranslation {
    private final Long unixTime;

    public DateTranslation(Long unixTime) {
        this.unixTime = unixTime;
    }

    public void unix2YYYYMMDD() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd", Locale.CHINA);
        String formatted = dateFormat.format(new Date(unixTime));
        System.out.println("origin: " + unixTime + " ,format: YYYYMMdd , now: " + formatted);
    }

    public void unix2YYYY_MM_DD() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.CHINA);
        String formatted = dateFormat.format(new Date(unixTime));
        System.out.println("origin: " + unixTime + " ,format: YYYY-MM-dd , now: " + formatted);
    }

    public void unix2YYYY_MM_DD_HH_MM_SS() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.CHINA);
        String formatted = dateFormat.format(new Date(unixTime));
        System.out.println("origin: " + unixTime + " ,format: YYYY-MM-dd HH:mm:ss , now: " + formatted);
    }

    public void unix2UnixSeconds() {

        System.out.println("origin: " + unixTime + " ,format: unixSecond , now: " + unixTime / 1000);
    }


    public static void main(String[] args) {
        long unixTime = System.currentTimeMillis();
        System.out.println("now unix time: " + unixTime);

        DateTranslation handle = new DateTranslation(unixTime);
        handle.unix2YYYYMMDD();
        handle.unix2YYYY_MM_DD();
        handle.unix2YYYY_MM_DD_HH_MM_SS();
        handle.unix2UnixSeconds();
    }
}