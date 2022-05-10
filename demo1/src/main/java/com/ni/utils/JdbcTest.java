package com.ni.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.sap.db.jdbc.Driver");
        DriverManager.getConnection(
                "jdbc:sap://10.24.69.9:39017",
                "SYSTEM",
                "Hana135246");
    }
}
