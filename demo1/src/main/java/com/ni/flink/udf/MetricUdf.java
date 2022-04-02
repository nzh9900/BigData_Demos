package com.ni.flink.udf;

import org.apache.flink.metrics.Counter;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

public class MetricUdf extends ScalarFunction {
    private transient Counter counter;

    @Override
    public void open(FunctionContext context) throws Exception {
        this.counter = context
                .getMetricGroup()
                .counter("sumSinkRecords");
    }

    public Long eval() {
        counter.inc(10);
        return counter.getCount();
    }

}
