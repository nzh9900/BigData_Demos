package com.ni.dataStreamApi.connectors;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.descriptors.FormatDescriptor;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Schema;
import org.apache.flink.table.formats.raw.RawFormatDeserializationSchema;
import org.apache.flink.table.formats.raw.RawFormatFactory;
import org.apache.flink.table.types.DataType;
import org.apache.flink.types.Row;

import java.util.HashMap;
import java.util.Properties;

public class KafkaStreamSink {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        Schema schema = new Schema();
        schema.field("line", TypeInformation.of(String.class));
        tEnv
                .connect(getKafkaConnect())
                .withSchema(schema)
                //.withFormat()
                .inAppendMode()
                .createTemporaryTable("cdcTable");

        Table table = tEnv.from("cdcTable");
        TypeInformation<Row> typeInfo = table.getSchema().toRowType();
        DataStream<Row> stream = tEnv.toAppendStream(table, typeInfo);
        stream.print();

        env.execute();
    }

    private static Kafka getKafkaConnect() {
        Kafka kafka = new Kafka().version("universal");
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "hadoop102:9092,hadoop103:9092,hadoop104:9092");
        props.setProperty("group.id", "testCdcConsumer");
        kafka.topic("testCDC");
        kafka.properties(props);
        kafka.startFromLatest();
        return kafka;
    }
}
