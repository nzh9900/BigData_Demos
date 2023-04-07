package com.ni.dataStreamApi.connectors;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class KafkaUpsertAndCkSink {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        tEnv.executeSql("CREATE TABLE userTable (\n" +
                "  userId BIGINT,\n" +
                "  pv BIGINT,\n" +
                "  PRIMARY KEY (userId) NOT ENFORCED\n" +
                ") WITH (\n" +
                "  'connector' = 'upsert-kafka',\n" +
                "  'topic' = 'waterdrop_upsert',\n" +
                "  'properties.bootstrap.servers' = 'dev-new-cdh1.lab.com:9092,dev-new-cdh3.lab.com:9092,dev-new-cdh2.lab.com:9092',\n" +
                "  'key.format' = 'json',\n" +
                "  'value.format' = 'json'\n" +
                ")");

        tEnv.executeSql("CREATE TABLE clickhouseSink (\n" +
                "  userId BIGINT,\n" +
                "  pv BIGINT \n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                "   'url' = 'jdbc:clickhouse://10.24.65.126:8123/report_center',\n" +
                "   'table-name' = 'users',\n" +
                "   'username' = 'cheney',\n" +
                "   'password' = '#2021cheney'\n" +
                ")");

        tEnv.executeSql("insert into clickhouseSink select * from userTable");


        env.execute();
    }
}
