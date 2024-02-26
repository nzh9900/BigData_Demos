package com.ni.flink;

import com.ni.flink.function.CountMessageFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * 通过Flink metric监控类型为source和sink的flink sql的输入输出数据条数
 */
public class FlinkSqlMetric {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.executeSql("CREATE TABLE fact_table (\n" +
                "  `id` BIGINT,\n" +
                "  `ts` TIMESTAMP(3) METADATA FROM 'timestamp',\n" +
                "  `process_time` as proctime()\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'abcd',\n" +
                "  'properties.bootstrap.servers' = '10.24.68.224:9092',\n" +
                "  'properties.group.id' = 'testGroup1',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'format' = 'csv'\n" +
                ")");

        tEnv.createTemporaryFunction("count_message", new CountMessageFunction("fact_table_1"));

        TableResult tableResult = tEnv.executeSql("select count_message(0) from fact_table");
        tableResult.print();

        tEnv.executeSql("CREATE TABLE blackhole_table WITH ('connector' = 'blackhole')\n" +
                "LIKE fact_table (EXCLUDING ALL)");

        tEnv.executeSql("INSERT INTO blackhole_table SELECT id FROM fact_table");

        tEnv.execute("metric test");
    }
}