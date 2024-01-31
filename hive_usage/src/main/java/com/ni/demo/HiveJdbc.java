package com.ni.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hive.beeline.logs.BeelineInPlaceUpdateStream;
import org.apache.hive.jdbc.HiveStatement;
import org.apache.hive.jdbc.logs.InPlaceUpdateStream;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
public class HiveJdbc {
    public static void main(String[] args) throws SQLException {
        HiveJdbc hiveJdbc = new HiveJdbc();
        //String jdbcUrl = "jdbc:hive2://node22.test.com:10000/default;principal=hive/node20.test.com@TEST.COM";
        String jdbcUrl = "jdbc:hive2://kafka03.test.com:10000/default;principal=hive/kafka03.test.com@TEST.COM";
        String principal = "idp";
        String keytabFile = "/opt/idp.keytab";
        try (Connection connection = hiveJdbc.getConnection(jdbcUrl, principal, keytabFile)) {
            hiveJdbc.executeSql(connection, "select count(1) from default.tag");
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
            InPlaceUpdateStream.EventNotifier eventNotifier =
                    new InPlaceUpdateStream.EventNotifier();
            ByteArrayOutputStream logStream = new ByteArrayOutputStream();
            Thread logThread = new Thread(createQueryLogRunnable(statement, eventNotifier, logStream));
            logThread.setDaemon(true);
            logThread.start();
            if (statement instanceof HiveStatement) {
                HiveStatement hiveStatement = (HiveStatement) statement;
                hiveStatement.setInPlaceUpdateStream(
                        new BeelineInPlaceUpdateStream(new PrintStream(logStream),
                                eventNotifier)
                );
            }
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
                                           InPlaceUpdateStream.EventNotifier eventNotifier,
                                           ByteArrayOutputStream logStream) {
        if (statement instanceof HiveStatement) {
            HiveStatement hiveStatement = (HiveStatement) statement;
            return () -> {
                while (true) {
                    try {
                        List<String> queryLogList = hiveStatement.getQueryLog();
                        if (!queryLogList.isEmpty()) eventNotifier.operationLogShowedToUser();
                        for (String log : queryLogList) {
                            logStream.write(log.getBytes());
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