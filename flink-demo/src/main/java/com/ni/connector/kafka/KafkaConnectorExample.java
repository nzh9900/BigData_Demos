package com.ni.connector.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

public class KafkaConnectorExample {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env =
                StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        Properties sourceProp = new Properties();
        sourceProp.put("bootstrap.servers", "localhost:9092");
        sourceProp.put("group.id", "flink.learn.1");
        // 建立source
        DataStreamSource<String> source = env.addSource(
                new FlinkKafkaConsumer<>(
                        "flink.learn.source",
                        new SimpleStringSchema(),
                        sourceProp
                ).setStartFromEarliest());

        // 处理source
        SingleOutputStreamOperator<String> middle =
                source.map(element -> String.valueOf(Integer.parseInt(element) * 2));

        // 建立sink
        Properties sinkProp = new Properties();
        sinkProp.put("bootstrap.servers", "localhost:9092");
        FlinkKafkaProducer<String> sink = new FlinkKafkaProducer<>(
                "flink.learn.sink",
                new SimpleStringSchema(),
                sinkProp);

        // 输出
        middle.addSink(sink);

        env.execute("Kafka test");
    }
}
