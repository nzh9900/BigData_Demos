package com.ni.demo;

import java.sql.*;

/**
 * @ClassName HiveJdbcWithLDAP
 * @Description 未实现
 * @Author zihao.ni
 * @Date 2024/2/18 10:34
 * @Version 1.0
 **/
public class HiveJdbcWithLDAP {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        String jdbcUrl = "jdbc:hive2://kafka03.test.com:10000/default;principal=hive/kafka03.test.com@TEST.COM";
        String userName = "idp";
        String passwd = "Ab123456";
        Connection connection = DriverManager.getConnection(jdbcUrl, userName, passwd);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("show databases");
        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
        }

    }
}