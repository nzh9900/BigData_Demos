package com.flink.ni.demo.chapter1.meter;

import org.apache.flink.metrics.Meter;

/**
 * @ClassName MyMeter
 * @Description
 * @Author zihao.ni
 * @Date 2023/10/26 14:58
 * @Version 1.0
 **/
public class MyMeter implements Meter {
    @Override
    public void markEvent() {

    }

    @Override
    public void markEvent(long l) {

    }

    @Override
    public double getRate() {
        return 0;
    }

    @Override
    public long getCount() {
        return 0;
    }
}