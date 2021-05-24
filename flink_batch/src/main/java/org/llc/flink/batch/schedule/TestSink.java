package org.llc.flink.batch.schedule;

import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

public class TestSink implements SinkFunction<String> {


    @Override
    public void invoke(String value, SinkFunction.Context context) throws Exception {
        System.out.println("sink ===> " + value);
    }

}
