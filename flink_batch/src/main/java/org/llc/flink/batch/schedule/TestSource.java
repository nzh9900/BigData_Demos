package org.llc.flink.batch.schedule;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

public class TestSource implements SourceFunction<String> {

    int i = 1;

    @Override
    public void run(SourceContext sourceContext) throws Exception {

        sourceContext.collect("hello");
        sourceContext.collectWithTimestamp(1, 100);


    }

    @Override
    public void cancel() {

    }
}
