package com.ni.dataStreamApi.connectors;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;

public class JdbcConnector {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().useOldPlanner().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, settings);

        DataStreamSource<Tuple2<String, Long>> source = env.fromElements(new Tuple2<String, Long>("a", 20L), new Tuple2<String, Long>("b", 30L));
        Table table = tEnv.fromDataStream(source);

        DataStream<Row> rowDataStream = tEnv.toAppendStream(table, table.getSchema().toRowType());


        rowDataStream.addSink(JdbcSink.sink(
                "insert into report_center.waterdrop_test(name,age) values(?,?)",
                (ps, row) -> {
                    for (int i = 0; i < row.getArity(); i++) {
                        Object field = row.getField(i);
                        ps.setObject(i + 1, field);
                    }
                }
                , new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:clickhouse://10.24.65.126:8123")
                        .withDriverName("ru.yandex.clickhouse.ClickHouseDriver")
                        .withUsername("cheney")
                        .withPassword("#2021cheney")
                        .build()));
        env.execute();
    }
}
