package com.flink.ni.demo.function;

import com.flink.ni.demo.meter.MyMeter;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Meter;

/**
 * @ClassName MyNewRichFunction
 * @Description todo
 * @Author zihao.ni
 * @Date 2023/10/26 14:54
 * @Version 1.0
 **/
public class MyNewRichFunction extends RichMapFunction<org.apache.flink.types.Row, Object> {
    private Meter meter;

    @Override
    public void open(Configuration parameters) throws Exception {
        this.meter = getRuntimeContext().getMetricGroup().meter("myMeter", new MyMeter());
        super.open(parameters);
    }

    @Override
    public org.apache.flink.types.Row map(org.apache.flink.types.Row o) throws Exception {
        this.meter.markEvent();
        return o;
    }
}