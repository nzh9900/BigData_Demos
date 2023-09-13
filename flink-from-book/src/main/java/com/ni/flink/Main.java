package com.ni.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Main {
    public static void main(String[] args) {
        StreamExecutionEnvironment.getExecutionEnvironment();
        System.out.println("Hello world!");
    }
}