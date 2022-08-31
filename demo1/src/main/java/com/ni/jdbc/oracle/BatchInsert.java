package com.ni.jdbc.oracle;

import java.math.BigDecimal;
import java.sql.*;

public class BatchInsert {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        batchInsert("jdbc:oracle:thin:@package:1522:helowin", "FLINKUSER", "flinkpw");
    }


    private static void batchInsert(String url, String user, String password) throws SQLException, ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection conn = DriverManager.getConnection(url, user, password);

        //PreparedStatement preparedStatement = conn.prepareStatement("MERGE INTO \"STUDENT\" T1 USING (SELECT  :1  \"ID\",  :2  \"NAME\",  :3  \"SCHOOL\",  :4  \"NICKNAME\",  :5  \"AGE\",  :6  \"CLASS_NUM\",  :7  \"SCORE\",  :8  \"PHONE\",  :9  \"EMAIL\",  :10  \"IP\",  :11  \"ADDRESS\" FROM DUAL) T2 ON (T1.\"ID\"=T2.\"ID\")  WHEN MATCHED THEN UPDATE SET \"T1\".\"NAME\" = \"T2\".\"NAME\",\"T1\".\"SCHOOL\" = \"T2\".\"SCHOOL\",\"T1\".\"NICKNAME\" = \"T2\".\"NICKNAME\",\"T1\".\"AGE\" = \"T2\".\"AGE\",\"T1\".\"CLASS_NUM\" = \"T2\".\"CLASS_NUM\",\"T1\".\"SCORE\" = \"T2\".\"SCORE\",\"T1\".\"PHONE\" = \"T2\".\"PHONE\",\"T1\".\"EMAIL\" = \"T2\".\"EMAIL\",\"T1\".\"IP\" = \"T2\".\"IP\",\"T1\".\"ADDRESS\" = \"T2\".\"ADDRESS\" WHEN NOT MATCHED THEN INSERT (\"ID\",\"NAME\",\"SCHOOL\",\"NICKNAME\",\"AGE\",\"CLASS_NUM\",\"SCORE\",\"PHONE\",\"EMAIL\",\"IP\",\"ADDRESS\") VALUES (T2.\"ID\",T2.\"NAME\",T2.\"SCHOOL\",T2.\"NICKNAME\",T2.\"AGE\",T2.\"CLASS_NUM\",T2.\"SCORE\",T2.\"PHONE\",T2.\"EMAIL\",T2.\"IP\",T2.\"ADDRESS\")");
        //PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO \"STUDENT\"(\"ID\", \"NAME\", \"SCHOOL\", \"NICKNAME\", \"AGE\", \"CLASS_NUM\", \"SCORE\", \"PHONE\", \"EMAIL\", \"IP\", \"ADDRESS\") VALUES (:1 , :2 , :3 , :4 , :5 , :6 , :7 , :8 , :9 , :10 , :11 )");
        //PreparedStatement preparedStatement = conn.prepareStatement("MERGE INTO \"STUDENT\" T1 USING (SELECT  ?  \"ID\",  ?  \"NAME\",  ?  \"SCHOOL\",  ?  \"NICKNAME\",  ?  \"AGE\",  ?  \"CLASS_NUM\",  ?  \"SCORE\",  ?  \"PHONE\",  ?  \"EMAIL\",  ?  \"IP\",  ?  \"ADDRESS\" FROM DUAL) T2 ON (T1.\"ID\"=T2.\"ID\")  WHEN MATCHED THEN UPDATE SET \"T1\".\"NAME\" = \"T2\".\"NAME\",\"T1\".\"SCHOOL\" = \"T2\".\"SCHOOL\",\"T1\".\"NICKNAME\" = \"T2\".\"NICKNAME\",\"T1\".\"AGE\" = \"T2\".\"AGE\",\"T1\".\"CLASS_NUM\" = \"T2\".\"CLASS_NUM\",\"T1\".\"SCORE\" = \"T2\".\"SCORE\",\"T1\".\"PHONE\" = \"T2\".\"PHONE\",\"T1\".\"EMAIL\" = \"T2\".\"EMAIL\",\"T1\".\"IP\" = \"T2\".\"IP\",\"T1\".\"ADDRESS\" = \"T2\".\"ADDRESS\" WHEN NOT MATCHED THEN INSERT (\"ID\",\"NAME\",\"SCHOOL\",\"NICKNAME\",\"AGE\",\"CLASS_NUM\",\"SCORE\",\"PHONE\",\"EMAIL\",\"IP\",\"ADDRESS\") VALUES (T2.\"ID\",T2.\"NAME\",T2.\"SCHOOL\",T2.\"NICKNAME\",T2.\"AGE\",T2.\"CLASS_NUM\",T2.\"SCORE\",T2.\"PHONE\",T2.\"EMAIL\",T2.\"IP\",T2.\"ADDRESS\")");




        PreparedStatement preparedStatement = conn.prepareStatement("MERGE INTO \"FLINKUSER\".\"STUDENT\" T1 USING (SELECT  ? \"ID\",  ? \"NAME\",  ? \"SCHOOL\",  ? \"NICKNAME\",  ? \"AGE\",  ? \"CLASS_NUM\",  ? \"SCORE\",  ? \"PHONE\",  ? \"EMAIL\",  ? \"IP\",  ? \"ADDRESS\" FROM DUAL) T2 ON (T1.\"ID\"=T2.\"ID\")  WHEN MATCHED THEN UPDATE SET \"T1\".\"NAME\" = \"T2\".\"NAME\",\"T1\".\"SCHOOL\" = \"T2\".\"SCHOOL\",\"T1\".\"NICKNAME\" = \"T2\".\"NICKNAME\",\"T1\".\"AGE\" = \"T2\".\"AGE\",\"T1\".\"CLASS_NUM\" = \"T2\".\"CLASS_NUM\",\"T1\".\"SCORE\" = \"T2\".\"SCORE\",\"T1\".\"PHONE\" = \"T2\".\"PHONE\",\"T1\".\"EMAIL\" = \"T2\".\"EMAIL\",\"T1\".\"IP\" = \"T2\".\"IP\",\"T1\".\"ADDRESS\" = \"T2\".\"ADDRESS\" WHEN NOT MATCHED THEN INSERT (\"ID\",\"NAME\",\"SCHOOL\",\"NICKNAME\",\"AGE\",\"CLASS_NUM\",\"SCORE\",\"PHONE\",\"EMAIL\",\"IP\",\"ADDRESS\") VALUES (T2.\"ID\",T2.\"NAME\",T2.\"SCHOOL\",T2.\"NICKNAME\",T2.\"AGE\",T2.\"CLASS_NUM\",T2.\"SCORE\",T2.\"PHONE\",T2.\"EMAIL\",T2.\"IP\",T2.\"ADDRESS\")");

        preparedStatement.setString(1, "oplj");
        preparedStatement.setString(2, "senDD");
        preparedStatement.setString(3, "ui");
        preparedStatement.setString(4, "ui");
        preparedStatement.setInt(5, 10);
        preparedStatement.setInt(6, 11);
        preparedStatement.setBigDecimal(7, new BigDecimal("22.09"));
        preparedStatement.setLong(8, 213124251L);
        preparedStatement.setString(9, "oplj@ww.com");
        preparedStatement.setString(10, "102.211.22.11");
        preparedStatement.setInt(11, 2131);

        preparedStatement.execute();

    }

}
