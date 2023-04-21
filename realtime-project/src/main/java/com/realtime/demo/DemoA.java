package com.realtime.demo;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @ClassName DemoA
 * @Description
 * @Author zihao.ni
 * @Date 2023/4/10 15:46
 * @Version 1.0
 **/
public class DemoA {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(new Configuration());
        env.setParallelism(2);
        String[] strings = {"a", "b", "c"};
        DataStreamSource<String> dataSource = env.fromCollection(Arrays.asList(strings));
        SingleOutputStreamOperator<String> middleMap = dataSource.map(item -> item + "123");
        middleMap.print();
        env.execute("add 123");
    }

}