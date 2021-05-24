package org.llc.flink.batch.schedule;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Client {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> dataStream = senv.addSource(new TestSource());

        dataStream.addSink(new TestSink());

        senv.execute("test");



    }
}
