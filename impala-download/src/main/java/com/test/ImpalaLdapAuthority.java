package com.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.*;

/**
 * @ClassName ImpalaLdapAuthority
 * @Description 使用ldap的方式登陆impala
 * @Author zihao.ni
 * @Date 2023/5/19 11:42
 * @Version 1.0
 **/
public class ImpalaLdapAuthority {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException, UnsupportedEncodingException {
        handle();
    }

    private static void handle() throws ClassNotFoundException, SQLException, InterruptedException, UnsupportedEncodingException {
        String driver = "com.cloudera.impala.jdbc41.Driver";
        String jdbcUrl = "jdbc:impala://node21.test.com:21052;AuthMech=3";
        String username = "xxx";
        String password = "xxx";
        Class.forName(driver);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement()) {
            statement.execute("select * from default.test_abcx limit 10");
            ResultSet resultSet = statement.getResultSet();
            int columnCount = resultSet.getMetaData().getColumnCount();
            System.out.println(columnCount);
            System.out.println(resultSet.getMetaData().getColumnTypeName(1));
            ResultSetMetaData metaData = resultSet.getMetaData();
            StringBuffer buffer1 = new StringBuffer();
            for (int i = 1; i <= columnCount; i++) {
                buffer1.append(metaData.getColumnName(i)).append(",");
            }
            System.out.println(buffer1);


            while (resultSet.next()) {
                StringBuffer buffer = new StringBuffer();
                for (int i = 1; i <= columnCount; i++) {
                    String record = resultSet.getString(i);
                    if (record!=null){
                        record = new String(record.getBytes(Charset.forName("UTF-8")), "GBK");
                    }
                    buffer.append(record).append(",");
                }
                System.out.println(buffer);
                Thread.sleep(1000);
            }
        }

    }
}