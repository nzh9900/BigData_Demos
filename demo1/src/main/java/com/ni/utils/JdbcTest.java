package com.ni.utils;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) throws Exception {
        JdbcTest jdbcTest = new JdbcTest();
        jdbcTest.oceanbaseConnection();
    }

    public void hanaConnection(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.sap.db.jdbc.Driver");
        DriverManager.getConnection(
                "jdbc:sap://10.24.69.9:39017",
                "SYSTEM",
                "Hana135246");
    }

    public void oceanbaseConnection() throws Exception {
        Class.forName("com.alipay.oceanbase.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:oceanbase://10.24.69.33:2881/oceanbase?sessionVariables=ob_trx_timeout=888800&sessionVariables=net_read_timeout=888800&allowLoadLocalInfile=false&autoDeserialize=false&allowLocalInfile=false&allowUrlInLocalInfile=false",
                "root@sys",
                "123456");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("show tables");
        while (resultSet.next()) {
            System.out.println("database: " + resultSet.getString(1));
        }
    }
}
