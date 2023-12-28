package com.ni.demo;

import java.sql.SQLException;

/**
 * @ClassName HiveJdbc
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/12/27 14:18
 * @Version 1.0
 **/
public class HiveJdbc {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        //Configuration conf = new Configuration();
        //String principal = "idp";
        //String keytabFile = "/opt/idp.keytab";
        //conf.set("hadoop.security.authentication", "kerberos");
        //conf.set("hadoop.security.authorization", "true");
        //UserGroupInformation.setConfiguration(conf);
        //try {
        //    UserGroupInformation.loginUserFromKeytab(principal, keytabFile);
        //    System.out.println("认证方式和用户: " + UserGroupInformation.getLoginUser());
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        try {
            java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:hive2://cherry.lab.com:10000/default;principal=hive/cherry.lab.com@RUISDATA.COM");
            java.sql.Statement statement = connection.createStatement();
            String sql = "show databases";
            java.sql.ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}