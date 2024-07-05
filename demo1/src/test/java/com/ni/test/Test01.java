package com.ni.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName Test01
 * @Description
 * @Author zihao.ni
 * @Date 2023/4/20 16:42
 * @Version 1.0
 **/
public class Test01 {
    @Test
    public void testA() throws UnsupportedEncodingException {
        String a = "���������������������";
        String s = new String(a.getBytes(), "UTF-8");
        System.out.println(s);
    }

    @Test
    public void testB() {
        RuntimeException runtimeException = new RuntimeException();
        System.out.println(runtimeException.getMessage());
        System.out.println("====================");
        runtimeException.printStackTrace();
        System.out.println("====================");
        System.out.println(runtimeException.getLocalizedMessage());
    }

    @Test
    public void testFileCreation() {
        LocalDate nowDate = LocalDate.now();
        int day = nowDate.getDayOfMonth();
        int month = nowDate.getMonthValue();
        int year = nowDate.getYear();
        File file = new File(String.format("/tmp/principal/%s-%s/%s-%s-%s", year, month, year, month, day));
        System.out.println(file.exists());
        file.delete();
        System.out.println(file.exists());

        if (!file.exists()) {
            file.mkdirs();
            file.setWritable(true, false);
            file.setReadable(true, false);
            file.setExecutable(true, false);
            file.getParentFile().setWritable(true, false);
            file.getParentFile().setReadable(true, false);
            file.getParentFile().setExecutable(true, false);
            file.getParentFile().getParentFile().setWritable(true, false);
            file.getParentFile().getParentFile().setReadable(true, false);
            file.getParentFile().getParentFile().setExecutable(true, false);
        }
    }


    @Test
    public void testRegex() {
        System.out.println(Pattern.matches("[\\x00-\\x1f\\x7f]", ""));

    }

    @Test
    public void reverseString() {
        String a = "fact_transaction_detail_label";
        System.out.println(StringUtils.reverse(a));
    }

    @Test
    public void printFileSeparator() {
        System.out.println(File.separator);
        System.out.println(File.pathSeparator);
    }

    @Test
    public void getYarnState() {
        try {
            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod("http://10.24.32.23:8088/ws/v1/cluster/apps" + "/" + "application_1667201981464_146355");
            httpClient.executeMethod(getMethod);
            String response = getMethod.getResponseBodyAsString().trim();
            JSONObject jsonObject = JSON.parseObject(response);
            System.out.println(jsonObject);
            getMethod.releaseConnection();
        } catch (Exception e) {
            System.out.println("error");
        }
    }


    @Test
    public void testDateFormat() {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        System.out.println(formatter.parse("2019-07-01 00:12:31.7908120"));
    }

    @Test
    public void testDorisClusterConnection() throws ClassNotFoundException {
        // 使用mysql jdbc连接数据库
        String url = "jdbc:mysql:loadbalance://database:9031,database:9032,database:9033?useSSL=false";
        String user = "root";
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(url, user, "");) {
            PreparedStatement statement = connection.prepareStatement("select 1");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void writeFile() throws IOException {
        FileWriter fileWriter = new FileWriter("/Users/ni/test.csv");
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 10; j++) {
                fileWriter.write(String.valueOf(j) + " ");
            }
            fileWriter.write("\n");
            fileWriter.flush();
        }
    }

    @Test
    public void testPattern() {
        String query = "MERGE INTO test.sink_test_pk t  USING (SELECT ? id, ? name FROM DUAL LIMIT 1 OVER(PARTITION BY id ORDER BY id)) s  ON (t.id=s.id)  WHEN MATCHED THEN UPDATE SET id=s.id, name=s.name WHEN NOT MATCHED THEN INSERT (id, name) VALUES (s.id, s.name)";
        Pattern compile = Pattern.compile(
                "\\s*MERGE\\s*INTO\\s*([\\w.]+)\\s*.*USING\\s*.*\\s*SELECT\\s*(.*)\\s*FROM\\s*(\\w*).*\\s*ON\\s*(.*)\\s*WHEN\\s*MATCHED\\s*.*");
        Matcher matcher = compile.matcher(query);
        matcher.find();
        System.out.println(matcher.group(1));
        System.out.println(matcher.group(2));
        System.out.println(matcher.group(3));
    }
}