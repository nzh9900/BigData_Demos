package com.ni.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GenerateDateUtil {
    public static void main(String[] args) throws IOException {
        Calendar instance = Calendar.getInstance();
        instance.set(2024, Calendar.JANUARY, 1);
        Date time = instance.getTime();
        printDate(365, time, ContentFormat.TEXT);
    }

    private static void printDate(long numberOfDays, Date startDate, ContentFormat contentFormat) throws IOException {
        File file = new File("/tmp/date-Template.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        for (int i = 0; i < numberOfDays; i++) {
            String format;
            if (ContentFormat.SQL.equals(contentFormat)) {
                format = "-- =============================================%s=============================================%n%n%n%n%n%n%n%n%n%n";
            } else if (ContentFormat.TEXT.equals(contentFormat)) {
                format = "=============================================%s=============================================%n%n%n%n%n%n%n%n%n%n";
            } else {
                format = "=============================================%s=============================================%n%n%n%n%n%n%n%n%n%n";
            }
            bufferedWriter.append(
                    String.format(
                            format,
                            sdf.format(startDate)));

            gregorianCalendar.setTime(startDate);
            gregorianCalendar.add(Calendar.DATE, 1);
            startDate = gregorianCalendar.getTime();
        }

        bufferedWriter.flush();
        bufferedWriter.close();

    }
}

enum ContentFormat {
    SQL,
    TEXT
}
