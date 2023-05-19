package com.test;

import java.sql.*;

/**
 * @ClassName ImpalaLdapAuthority
 * @Description 使用ldap的方式登陆impala
 * @Author zihao.ni
 * @Date 2023/5/19 11:42
 * @Version 1.0
 **/
public class ImpalaLdapAuthority {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        handle();
    }

    private static void handle() throws ClassNotFoundException, SQLException {
        String driver = "com.cloudera.impala.jdbc41.Driver";
        String jdbcUrl = "jdbc:impala://xxxxxxx:21052;AuthMech=3";
        String username = "xxx";
        String password = "xxxxx";
        Class.forName(driver);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute("select * from default.null_test");
            ResultSet resultSet = statement.getResultSet();
            System.out.println(resultSet.getMetaData().getColumnCount());
            System.out.println(resultSet.getMetaData().getColumnTypeName(1));
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        }

    }
}