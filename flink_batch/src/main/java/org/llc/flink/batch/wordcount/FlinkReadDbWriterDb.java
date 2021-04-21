package org.llc.flink.batch.wordcount;

import java.util.Date;

import org.apache.flink.api.java.tuple.Tuple10;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkReadDbWriterDb {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple10<String, String, String, String, String, String, String, String, String, String>> dataStream = env
                .addSource(new Flink2JdbcReader());

        // tranfomat

//        dataStream.addSink(new Flink2JdbcWriter());
        env.execute("Flink cost DB data to write Database");

    }
}