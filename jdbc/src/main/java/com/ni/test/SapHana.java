package com.ni.test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @ClassName SapHana
 * @Description
 * @Author zihao.ni
 * @Date 2024/4/9 15:17
 * @Version 1.0
 **/
public class SapHana {
    public static void main(String[] args) throws Exception {
        Class<?> clazz =
                Class.forName("com.sap.db.jdbc.Driver", true, Thread.currentThread().getContextClassLoader());
        Driver driver = (Driver) clazz.newInstance();

        //DriverManager.getConnection("jdbc:sap://10.24.96.223:39017/TEST", "SYSTEM", "Hana135246");
        Properties info = new Properties();
        info.put("user", "SYSTEM");
        info.put("password", "Hana135246");
        Connection connection = driver.connect("jdbc:sap://10.24.96.223:39017/TEST", info);


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from TEST.TEST_IN");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
    }
}