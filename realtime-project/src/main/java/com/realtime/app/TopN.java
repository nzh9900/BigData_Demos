package com.realtime.app;


import com.realtime.entity.UserBehavior;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;

/**
 * @ClassName TopN
 * @Description 每隔5分钟输出最近一小时内点击量最多的前N个商品
 * @Author zihao.ni
 * @Date 2023/4/10 17:23
 * @Version 1.0
 **/
public class TopN {

    public static void main(String[] args) throws Exception {
        TopN topN = new TopN();
        topN.handle();
        System.exit(257);
    }

    private void handle() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置水印的生命周期，默认是200毫秒，即每个200毫秒生成一次水印
        env.getConfig().setAutoWatermarkInterval(500);

        env.setParallelism(5);

        // 读取文件

        SingleOutputStreamOperator<UserBehavior> data = env.readTextFile("realtime-project/src/main/resources/data/UserBehavior.csv", "utf-8")
                .setParallelism(1)
                .map(item -> {
                    String[] values = item.split(",");
                    return UserBehavior
                            .builder()
                            .userId(Long.valueOf(values[0]))
                            .itemId(Long.valueOf(values[1]))
                            .categoryId(Integer.valueOf(values[2]))
                            .behavior(values[3])
                            .timestamp(Long.valueOf(values[4]))
                            .build();
                });
        // 设置watermark策略为乱序有界，超时时间为10s
//                .assignTimestampsAndWatermarks(
//                        WatermarkStrategy
//                                .forBoundedOutOfOrderness(Duration.ofSeconds(10))
//                                .withTimestampAssigner()
//                );

        System.out.println(data.getId());

//        data.print();

        env.execute();
    }
}