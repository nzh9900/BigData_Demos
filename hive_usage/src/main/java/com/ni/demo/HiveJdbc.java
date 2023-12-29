package com.ni.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.security.PrivilegedExceptionAction;
import java.sql.*;

/**
 * @ClassName HiveJdbc
 * @Description
 * @Author zihao.ni
 * @Date 2023/12/27 14:18
 * @Version 1.0
 **/
public class HiveJdbc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        HiveJdbc hiveJdbc = new HiveJdbc();
        String jdbcUrl = "jdbc:hive2://node22.test.com:10000/default;principal=hive/node20.test.com@TEST.COM";
        String principal = "idp";
        String keytabFile = "/opt/idp.keytab";
        try (Connection connection = hiveJdbc.getConnection(jdbcUrl, principal, keytabFile)) {
            hiveJdbc.executeSql(connection, "show databases");
        }

    }

    private Connection getConnection(String jdbcUrl, String principal, String keytabFile) {
        Connection connection = null;
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            Configuration conf = new Configuration();
            conf.set("hadoop.security.authentication", "kerberos");
            conf.set("hadoop.security.authorization", "true");
            UserGroupInformation.setConfiguration(conf);
            UserGroupInformation ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI(principal, keytabFile);
            connection = ugi.doAs((PrivilegedExceptionAction<Connection>) () ->
                    DriverManager.getConnection(jdbcUrl, principal, "")
            );
            System.out.println("认证方式和用户: " + UserGroupInformation.getLoginUser());
        } catch (Exception e) {
            throw new RuntimeException("get connection error", e);
        }
        return connection;
    }

    public void executeSql(Connection connection, String sql) {
        try (Statement statement = connection.createStatement()) {
            java.sql.ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    String value = resultSet.getString(i);
                    sb.append(value).append("\t");
                }
                System.out.println(sb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}