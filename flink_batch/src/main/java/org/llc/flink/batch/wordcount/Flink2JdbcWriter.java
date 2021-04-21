package org.llc.flink.batch.wordcount;


import java.sql.DriverManager;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Flink2JdbcWriter extends RichSinkFunction<Tuple3<String, String, String>> {
    private static final long serialVersionUID = -8930276689109741501L;

    private Connection connect = null;
    private PreparedStatement ps = null;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Class.forName("com.mysql.jdbc.Driver");
        connect = (Connection) DriverManager.getConnection("jdbc:mysql://www.basic-auth.com:3305/analyses?characterEncoding=utf-8&useSSL=false", "llc", "Simicas@2021");
        ps = (PreparedStatement) connect.prepareStatement("insert into profit values (?,?,?,?)");
    }


    @Override
    public void invoke(Tuple3<String, String, String> value,
                       Context context) throws Exception {
        ps.setString(1, value.f0);
        ps.setString(2, value.f1);
        ps.setString(3, value.f2);
        ps.setDouble(4, 0.1D);
        ps.executeUpdate();
    }

    @Override
    public void close() {
        try {
            super.close();
            if (connect != null) {
                connect.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}