package org.llc.flink.table.connectors;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;


public class KafkaConnector {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment env = StreamTableEnvironment.create(executionEnv);

        env.executeSql("CREATE TABLE KafkaTable (\n" +
                "  `msg` STRING,\n" +
                "  `status` BIGINT,\n" +
                "  `current` STRING\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'mytest',\n" +
                "  'properties.bootstrap.servers' = 'hadoop102:9092,hadoop103:9092,hadoop104:9092',\n" +
                "  'properties.group.id' = 'myConsumer',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'format' = 'json'\n" +
                ")");

        TableResult tableResult = env.executeSql("select msg,status,`current` from KafkaTable");
        tableResult.print();

        env.execute("kafkaConnector");

    }
}
