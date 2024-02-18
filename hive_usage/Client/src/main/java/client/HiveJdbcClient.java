package client;

import com.ni.demo.HiveJdbcWithKerberos;

import java.sql.SQLException;

/**
 * @ClassName HiveJdbc
 * @Description
 * @Author zihao.ni
 * @Date 2023/12/27 14:18
 * @Version 1.0
 **/
public class HiveJdbcClient {
    public static void main(String[] args) throws SQLException {
        HiveJdbcWithKerberos hiveJdbcWithKerberos = new HiveJdbcWithKerberos();
        hiveJdbcWithKerberos.handle(new String[]{});
    }
}