package com.ni.flink;

import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @ClassName CaptureDDLChange
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/28 13:35
 * @Version 1.0
 **/
public class CaptureDDLChange {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(new Configuration());
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("10.24.96.222")
                .port(3307)
                .username("root")
                .password("123456")
                .databaseList("demo")
                .tableList("demo.stu_for_source")
                .deserializer(new JsonDebeziumDeserializationSchema())
                .includeSchemaChanges(true)
                .build();

        env.enableCheckpointing(3000);

        env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "mysql-source")
                .setParallelism(1)
                .print().setParallelism(1);

        env.execute();
    }
}