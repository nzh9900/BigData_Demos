package com.ni.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @ClassName Oracle
 * @Description
 * @Author zihao.ni
 * @Date 2023/12/21 10:36
 * @Version 1.0
 **/
public class Oracle {
    private final static Logger log = LoggerFactory.getLogger(Oracle.class);

    public static void main(String[] args) {
        String ip = "dataBase";
        int port = 1521;
        String sidUrl = getSidUrl(ip, port, "helowin2");
        String serviceNameUrl = getServiceNameUrl(ip, port, "helowin");
        String user = "test";
        String password = "Ab123456";
        try {
            log.info("get connection");
            Connection connection = DriverManager.getConnection(sidUrl, user, password);
            PreparedStatement preparedStatement = connection.prepareStatement("select 1 from dual");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSidUrl(String ip, int port, String sid) {
        return "jdbc:oracle:thin:@" + ip + ":" + port + ":" + sid;
    }

    public static String getServiceNameUrl(String ip, int port, String serviceName) {
        return "jdbc:oracle:thin:@//" + ip + ":" + port + "/" + serviceName;
    }
}