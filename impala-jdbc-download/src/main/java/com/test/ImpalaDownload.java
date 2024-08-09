package com.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class ImpalaDownload {
    /*
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        long startTime = System.currentTimeMillis();
        Connection conn = getConnection();
        //String sql = "select t2.uuid,mem_value,write_dt,rfm_m_avg,dmaddressstate from u_analysis_prod.tag as t1 join yili_dw.customer as t2 on t1.uuid = t2.uuid where t1.tag_id = '279';";
        String sql = "select uuid,mem_value,write_dt,rfm_m_avg,dmaddressstate from interface_edw.customer;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        File file = new File("/opt/cdp/backend/data.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int columnCount = rs.getMetaData().getColumnCount();

        // 获取字段名
        if (true) {
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rs.getMetaData().getColumnName(i);
                if (i == columnCount) {
                    fileOutputStream.write((columnName + "\n").getBytes(StandardCharsets.UTF_8));
                } else {
                    fileOutputStream.write((columnName + ",").getBytes(StandardCharsets.UTF_8));
                }
            }
        }

        System.out.println("列数:" + columnCount);
        int count = 0;
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                //String columnTypeName = rs.getMetaData().getColumnTypeName(i);
                String columnValue = rs.getString(i);
                if (columnValue == null) {
                    columnValue = "null";
                }
                fileOutputStream.write(i == columnCount ? columnValue.getBytes(StandardCharsets.UTF_8) :
                        (columnValue + ",").getBytes(StandardCharsets.UTF_8));
            }

            //  flush once every n rows
            if (count < 100000) {
                count += 1;
            } else {
                fileOutputStream.flush();
                count = 0;
            }
            fileOutputStream.write("\n".getBytes(StandardCharsets.UTF_8));
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        long endTime = System.currentTimeMillis();
        System.out.println("数据导出完成，共耗时" + (endTime - startTime) / 1000 + "s");
    }
    */

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement("select * from demo.demo_order");
        ResultSet resultSet = ps.executeQuery();
        System.out.println(resultSet.getMetaData().getColumnCount());
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = "org.apache.hive.jdbc.HiveDriver";
        //String url = "jdbc:hive2://cdhdata-15-15.bigdata.com:21050/;auth=noSasl";
        String url = "jdbc:hive2://10.24.69.3:21050/;principal=impala/dev-new-cdh1.lab.com@RUISDATA.COM";
        String username = "";
        String password = "";
        Connection conn = null;
        Class.forName(driver);
        //String jdbcUrl = "";
        String krbCfgPath = "/opt/cdp/backend/web/krb5.conf";
        String gssPath = "/opt/cdp/backend/impala_download/gss-jaas.conf";
        System.setProperty("java.security.auth.login.config", gssPath);
        System.setProperty("sun.security.jgss.debug", "true");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("java.security.krb5.conf", krbCfgPath);
        conn = DriverManager.getConnection(url,username,password);
        return conn;
    }

}
