package com.ni.flink.function;

import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.annotation.InputGroup;
import org.apache.flink.table.functions.FunctionContext;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * @ClassName CountMessageFunction
 * @Description
 * @Author zihao.ni
 * @Date 2024/2/23 16:47
 * @Version 1.0
 **/
public class CountMessageFunction extends ScalarFunction {
    private transient Counter counter;

    private final String counterName;

    public CountMessageFunction(String counterName) {
        this.counterName = counterName;
    }

    @Override
    public void open(FunctionContext context) throws Exception {
        MetricGroup metricGroup = context.getMetricGroup();
        this.counter = metricGroup.counter(counterName);
    }

    public String eval(@DataTypeHint(inputGroup = InputGroup.ANY) Object o) {
        counter.inc();
        System.out.println("count: " + counter.getCount());
        return o.toString();
    }
}