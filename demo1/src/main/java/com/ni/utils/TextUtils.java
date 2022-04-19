package com.ni.utils;

import java.io.*;

public class TextUtils {
    public static void main(String[] args) throws IOException {
        getInsertQueryForImpala(new File("/home/ni/桌面/厂商POC实时测试用例/t_ods_user_auth_kd.csv"), "t_ods_user_auth_kd");
    }

    public static void getInsertQueryForImpala(File file, String tableName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/home/ni/桌面/modified_" + file.getName())))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                writer.write("insert into table " + tableName + " values ");
                while ((line = reader.readLine()) != null) {
                    writer.write(String.format("(\"%s\"),", String.join("\",\"", line.split(","))));
                }
                writer.flush();
            }
        }
    }
}
