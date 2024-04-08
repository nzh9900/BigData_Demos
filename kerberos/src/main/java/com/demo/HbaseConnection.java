package com.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * @ClassName HbaseConnection
 * @Description
 * @Author zihao.ni
 * @Date 2024/4/7 10:04
 * @Version 1.0
 **/
public class HbaseConnection {

    public static void main(String[] args) throws IOException {
        HbaseConnection hbaseConnection = new HbaseConnection();
        Connection connection = hbaseConnection.getConnection();
        hbaseConnection.printClusterStatus(connection);
        hbaseConnection.printNamespace(connection);
        hbaseConnection.close(connection);
    }

    public Connection getConnection() throws IOException {
        kerberosInit();
        Configuration hbaseConf = HBaseConfiguration.create();
        hbaseConf.addResource(ClassLoader.getSystemResource("hbase-site.xml"));
        hbaseConf.addResource(ClassLoader.getSystemResource("core-site.xml"));
        return ConnectionFactory.createConnection(hbaseConf);
    }

    public void printClusterStatus(Connection connection) throws IOException {
        HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
        System.out.println(admin.getClusterStatus());
    }

    public void printNamespace(Connection connection) throws IOException {
        Admin admin = connection.getAdmin();
        TableName[] tableNames = admin.listTableNames();
        for (TableName tableName : tableNames) {
            System.out.println(Bytes.toString(tableName.getName()));
        }
    }

    public void close(Connection connection) {
        if (connection != null && !connection.isClosed()) {
            try {
                connection.close();
            } catch (IOException e) {
                System.out.println("close connection error");
            }
        }
    }

    private void kerberosInit() {
        try {
            UserGroupInformation.loginUserFromKeytab("idp", "/opt/idp.keytab");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}