package com.flink.ni.demo.demo;

import com.flink.ni.demo.function.MyNewRichFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @ClassName FlinkDemo01
 * @Description
 * @Author zihao.ni
 * @Date 2023/10/26 13:44
 * @Version 1.0
 **/
public class FlinkDemo01 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        tEnv.executeSql("CREATE TABLE IF NOT EXISTS `py_test` (\n" +
                "\t`ID`\tint \n" +
                ",\t`NAME`\tSTRING\n" +
                ",   `CC`    String\n" +
                ",\t`proctime` AS PROCTIME ()\n" +
                ") WITH (\n" +
                "\t'connector' = 'kafka',\n" +
                "\t'topic' = 'py_test',\n" +
                "\t'format' = 'json',\n" +
                "\t'properties.bootstrap.servers' = '10.24.68.224:9092',\n" +
                "\t'properties.group.id' = 'test_group',\n" +
                "\t'scan.startup.mode' = 'latest-offset'\n" +
                ")");
        Table table = tEnv.sqlQuery("select * from py_test");
        SingleOutputStreamOperator<Object> tableA = tEnv.toDataStream(table).map(new MyNewRichFunction());
        Table table1 = tEnv.fromDataStream(tableA);
        table1.execute().print();

        env.execute();
    }
}