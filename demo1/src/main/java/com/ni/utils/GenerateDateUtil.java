package com.ni.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GenerateDateUtil {
    public static void main(String[] args) throws IOException {
        printDate(100, new Date());
    }

    private static void printDate(long numberOfDays, Date startDate) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("/home/ni/文档/everything.txt"),true));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        for (int i = 0; i < numberOfDays; i++) {
            bufferedWriter.append(
                    String.format(
                            "=============================================%s=============================================%n%n%n%n%n%n%n%n%n%n",
                            sdf.format(startDate)));

            gregorianCalendar.setTime(startDate);
            gregorianCalendar.add(Calendar.DATE, 1);
            startDate = gregorianCalendar.getTime();
        }

        bufferedWriter.flush();
        bufferedWriter.close();

    }
}
