package com.ni.flink;

import com.ni.flink.function.CountMessageFunction;
import com.ni.flink.function.MeterFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.DataType;

/**
 * 通过Flink metric监控类型为source和sink的flink sql的输入输出数据条数
 */
public class FlinkSqlMetric {
    public static void main(String[] args) throws Exception {
        FlinkSqlMetric.kafkaTest(new String[]{""});
        //FlinkSqlMetric.cdcTest();

    }


    public static void cdcTest() {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.executeSql("CREATE TABLE IF NOT EXISTS `source_table` (\n" +
                "\t`id`\tVARCHAR(200)\n" +
                ",\t`name`\tVARCHAR(20)\n" +
                ",\t`school`\tVARCHAR(20)\n" +
                ",\t`nickname`\tVARCHAR(20)\n" +
                ",\t`age`\tINTEGER\n" +
                ",\t`class_num`\tINTEGER\n" +
                ",\t`score`\tDECIMAL(4,2)\n" +
                ",\t`phone`\tBIGINT\n" +
                ",\t`email`\tVARCHAR(64)\n" +
                ",\t`ip`\tVARCHAR(32)\n" +
                ",\t`address`\tSTRING\n" +
                ",\t`proctime` AS PROCTIME ()\n" +
                ",\tPRIMARY KEY (`id`) NOT ENFORCED\n" +
                ") WITH (\n" +
                "\t'connector' = 'mysql-cdc',\n" +
                "\t'hostname' = '10.24.96.222',\n" +
                "\t'port' = '3307',\n" +
                "\t'username' = 'root',\n" +
                "\t'password' = '123456',\n" +
                "\t'database-name' = 'demo',\n" +
                "\t'table-name' = 'stu_for_source',\n" +
                "\t'scan.startup.mode' = 'latest-offset'\n" +
                ")");

        DataType dataType = tEnv.from("source_table").getResolvedSchema().getColumn(0).get().getDataType();

        tEnv.createTemporaryFunction("count_message",
                new CountMessageFunction("MyGroup", "mysqlcdc", dataType));

        tEnv.executeSql("select count_message(id) as countA from source_table").print();
    }

    public static void kafkaTest(String[] args) throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());
        env.setParallelism(1);
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.executeSql("CREATE TABLE fact_table (\n" +
                "  `id` STRING,\n" +
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

        tEnv.executeSql("CREATE TABLE sink_table (\n" +
                "  `id` STRING,\n" +
                "  `ts` TIMESTAMP(3),\n" +
                "  `process_time` as proctime()\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'test',\n" +
                "  'properties.bootstrap.servers' = '10.24.68.224:9092',\n" +
                "  'properties.group.id' = 'testGroup1',\n" +
                "  'scan.startup.mode' = 'latest-offset',\n" +
                "  'format' = 'csv'\n" +
                ")");

        DataType dataType = tEnv.from("sink_table").getResolvedSchema().getColumn(0).get().getDataType();

        tEnv.createTemporaryFunction(
                "count_message",
                new MeterFunction(
                        "MyGroup",
                        "sourceKafkaCounter",
                        "sourceKafkaMeter",
                        dataType));
        tEnv.createTemporaryFunction(
                "count_sink",
                new MeterFunction(
                        "MyGroup",
                        "sinkKafkaCounter",
                        "sinkKafkaMeter",
                        dataType));

        tEnv.executeSql("insert into sink_table select id,ts as ts from (select count_sink(id) as id,ts as ts from fact_table)");

        tEnv.execute("metric test");
    }
}