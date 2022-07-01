package org.ni.table.Oracle;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class OracleTableCdcDemo01 {


    public static void main(String[] args) {
        StreamExecutionEnvironment ENV = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(ENV, settings);
        tEnv.getConfig().getConfiguration().setString("execution.checkpointing.interval", "3s");

        tEnv.executeSql("CREATE TABLE products (\n" +
                "     ID INT NOT NULL,\n" +
                "     NAME STRING,\n" +
                "     DESCRIPTION STRING,\n" +
                "     WEIGHT DECIMAL(10, 3),\n" +
                "     PRIMARY KEY(ID) NOT ENFORCED\n" +
                "     ) WITH (\n" +
                "     'connector' = 'oracle-cdc',\n" +
                "     'hostname' = 'package',\n" +
                "     'port' = '1522',\n" +
                "     'username' = 'flinkuser',\n" +
                "     'password' = 'flinkpw',\n" +
                "     'database-name' = 'helowin',\n" +
                "     'schema-name' = 'FLINKUSER',\n" +
                "     'table-name' = 'PRODUCT'," +
                //"     'scan.start.mode' = 'latest-offset'," +
                "     'debezium.log.mining.strategy'='online_catalog',\n" +
                "     'debezium.log.mining.continuous.mine'='true'," +
                "     'debezium.database.tablename.case.insensitive'='false'" +
                ")");

        tEnv.executeSql("select * from products").print();
    }
}
