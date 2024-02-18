package com.ni.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hive.jdbc.HiveStatement;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivilegedExceptionAction;
import java.sql.*;
import java.util.List;

/**
 * @ClassName HiveJdbc
 * @Description
 * @Author zihao.ni
 * @Date 2023/12/27 14:18
 * @Version 1.0
 **/
public class HiveJdbcWithKerberos {
    public void handle(String[] args) throws SQLException {
        HiveJdbcWithKerberos hiveJdbcWithKerberos = new HiveJdbcWithKerberos();
        String jdbcUrl = "jdbc:hive2://node22.test.com:10000/default;principal=hive/node20.test.com@TEST.COM";
        String principal = "idp";
        String keytabFile = "/opt/idp.keytab";
        try (Connection connection = hiveJdbcWithKerberos.getConnection(jdbcUrl, principal, keytabFile)) {
            hiveJdbcWithKerberos.executeSql(connection, "select count(1) from default.cat");
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
            ByteArrayOutputStream logStream = new ByteArrayOutputStream();
            Thread logThread = new Thread(createQueryLogRunnable(statement, logStream));
            logThread.setDaemon(true);
            logThread.start();
            java.sql.ResultSet resultSet = statement.executeQuery(sql);
            logThread.interrupt();
            logThread.join(10000);

            System.out.println(logStream.toString());

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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Runnable createQueryLogRunnable(Statement statement,
                                           ByteArrayOutputStream logStream) {
        if (statement instanceof HiveStatement) {
            HiveStatement hiveStatement = (HiveStatement) statement;
            return () -> {
                while (true) {
                    try {
                        List<String> queryLogList = hiveStatement.getQueryLog();
                        for (String log : queryLogList) {
                            logStream.write((log + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
                        }
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                }
            };
        } else {
            return () -> {

            };
        }
    }

    public Runnable createAllLogRunnable(Statement statement) {
        if (statement instanceof HiveStatement) {
            HiveStatement hiveStatement = (HiveStatement) statement;
            return () -> {
                while (hiveStatement.hasMoreLogs()) {
                    try {
                        List<String> queryLogList = hiveStatement.getQueryLog();
                        System.out.println("=============================");
                        queryLogList.forEach(queryLog -> {
                                    System.out.println(queryLog);
                                }
                        );
                        Thread.sleep(1000);
                    } catch (Exception e) {

                    }
                }
            };
        } else {
            return () -> {

            };
        }
    }
}