package com.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @ClassName ImpalaJdbcConnection
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/3/16 17:18
 * @Version 1.0
 **/
public class ImpalaJdbcConnection {
    public static void main(String[] args) throws Exception {
        String jdbcUrl = "jdbc:impala://node20.test.com:21052/default;AuthMech=1;KrbRealm=TEST.COM;KrbHostFQDN=node20.test.com;KrbServiceName=impala;";

        Properties props = new Properties();
        props.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        props.setProperty("java.security.auth.login.config", "kerberos/src/main/resources/jaas.conf");

        System.setProperty("java.security.krb5.conf", "kerberos/src/main/resources/krb5.conf");

        Class.forName("com.cloudera.impala.jdbc41.Driver");

        Connection conn = DriverManager.getConnection(jdbcUrl, props);

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("show databases");

        while (rs.next()) {
            System.out.println(rs.getString(1));
            System.out.println(rs.getString(2));
        }

        rs.close();
        stmt.close();
        conn.close();
    }

}