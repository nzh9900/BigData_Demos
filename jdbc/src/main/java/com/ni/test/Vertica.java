package com.ni.test;

import java.sql.*;
import java.util.Properties;

/**
 * @ClassName Vertica
 * @Description
 * @Author zihao.ni
 * @Date 2024/5/10 11:37
 * @Version 1.0
 **/
public class Vertica {
    private static final String SELECT_QUERY = "select ?,?";

    private static final String MERGE_QUERY = "MERGE INTO sink_test_pk st\n" +
            "USING (SELECT 1 as id_2,'321' as name_2) tp ON (st.id=st.id) " +
            "WHEN MATCHED THEN UPDATE SET name=tp.name_2 " +
            "WHEN NOT MATCHED THEN INSERT (id, name) VALUES (tp.id_2,tp.name_2);";

    private static final String STATEMENT_MERGE_QUERY = "MERGE INTO sink_test_pk st\n" +
            "USING (SELECT ? as id_2, ? as name_2) tp ON (st.id=st.id) " +
            "WHEN MATCHED THEN UPDATE SET name=tp.name_2 " +
            "WHEN NOT MATCHED THEN INSERT (id, name) VALUES (tp.id_2,tp.name_2);";

    private static final String INSERT_QUERY="insert into sink_test values(?,?)";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:vertica://10.24.96.223:5433/test";
        Class.forName("com.vertica.jdbc.Driver");
        Properties info = new Properties();
        info.setProperty("user", "test");
        info.setProperty("password", "testpwd");
        Connection connection = DriverManager.getConnection(url, info);
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setInt(1,2);
        preparedStatement.setString(2,"iop");
        boolean execute = preparedStatement.execute();
        System.out.println(preparedStatement.getUpdateCount());
    }
}