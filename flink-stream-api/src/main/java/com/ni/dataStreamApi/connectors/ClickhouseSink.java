package com.ni.dataStreamApi.connectors;

import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.util.ArrayList;

public class ClickhouseSink {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().useBlinkPlanner().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, settings);
        ArrayList<Tuple4<Long, String, Integer, Boolean>> tuple4s = new ArrayList<>();
        tuple4s.add(new Tuple4<Long, String, Integer, Boolean>(1L, "admin", 10, true));
        tuple4s.add(new Tuple4<Long, String, Integer, Boolean>(1L, "ulterman", 20000, false));
        DataStreamSource<Tuple4<Long, String, Integer, Boolean>> sourceStream = env.fromCollection(tuple4s);

        tEnv.getConfig().getConfiguration().setString("table.exec.sink.not-null-enforcer", "drop");

        tEnv.executeSql("CREATE TABLE KafkaTable (\n" +
                "  id BIGINT,\n" +
                "  name STRING,\n" +
                "  age INT,\n" +
                "  status BOOLEAN ,\n" +
                "  access_time  TIMESTAMP,  \n" +
                "  PRIMARY KEY (id) NOT ENFORCED, \n" +
                "  WATERMARK FOR access_time AS access_time - INTERVAL '30' SECOND \n" +
                ") WITH (\n" +
                "  'connector' = 'upsert-kafka',\n" +
                "  'topic' = 'keyed_topic3',\n" +
                "  'properties.bootstrap.servers' = 'hadoop102:9092',\n" +
                "  'properties.group.id' = 'keyed_topic3',\n" +
                "  'key.format' = 'json',\n" +
                "  'value.format' = 'json'\n" +
                ")");


/*        tEnv.executeSql("CREATE TABLE clickhouseSink (\n" +
                "  id BIGINT,\n" +
                "  name STRING,\n" +
                "  age INT,\n" +
                "  status BOOLEAN \n" +
                ") WITH (\n" +
                "   'connector' = 'clickhouse',\n" +
                "   'url' = 'jdbc:clickhouse://10.24.65.126:8123/report_center',\n" +
                "   'table-name' = 'users',\n" +
                "   'username' = 'cheney',\n" +
                "   'password' = '#2021cheney',\n" +
                "   'format' = 'json'\n" +
                ")");*/

        tEnv.executeSql("CREATE TABLE clickhouseSink (\n" +
                "  id BIGINT,\n" +
                "  name STRING,\n" +
                "  age INT,\n" +
                "  status BOOLEAN, \n" +
                "  primary key (id) not enforced \n" +
                ") WITH (\n" +
                "   'connector' = 'jdbc',\n" +
                //"   'url' = 'jdbc:clickhouse://10.24.65.126:8123/report_center',\n" +
                "   'url' = 'jdbc:mysql://localhost:3306/demo?useSSL=false',\n" +
                "   'table-name' = 'users',\n" +
                "   'username' = 'root',\n" +
                "   'password' = '123321'\n" +
                //"   'format' = 'json'\n" +
                ")");

        Table sourceTable = tEnv.from("KafkaTable");

        tEnv.executeSql("insert into clickhouseSink select id,name,age,status from KafkaTable").print();
    }
}
