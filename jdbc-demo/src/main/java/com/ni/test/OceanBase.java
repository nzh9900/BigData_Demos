package com.ni.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * @ClassName OceanBase
 * @Description
 * @Author zihao.ni
 * @Date 2023/8/25 10:12
 * @Version 1.0
 **/
public class OceanBase {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.oceanbase.jdbc.Driver");
        String url = "jdbc:oceanbase://docker01:2885/test";
        String username = "root@test";
        String passwd = "123456";
        try (
                Connection connection = DriverManager.getConnection(url, username, passwd);
                PreparedStatement preparedStatement =
                        connection.prepareStatement("create table octopus_cdp_new.alipay_customer_tmp as select * from octopus_cdp_new.alipay_customer");
        ) {
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}