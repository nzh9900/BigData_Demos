package org.llc.flink.stream.transform;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.llc.flink.stream.source.custom.CustomNoParallelSource;

/**
 *  broadcast分区规则
 */
public class StreamingDemoWithMyNoPralallelSourceBroadcast {

    public static void main(String[] args) throws Exception {
        //获取Flink的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);

        //获取数据源
        DataStreamSource<Long> text = env.addSource(new CustomNoParallelSource()).setParallelism(1);//注意：针对此source，并行度只能设置为1

        DataStream<Long> num = text.broadcast().map((MapFunction<Long, Long>) value -> {
            long id = Thread.currentThread().getId();
            System.out.println("线程id："+id+",接收到数据：" + value);
            return value;
        });

        //每2秒钟处理一次数据
        DataStream<Long> sum = num.windowAll(TumblingEventTimeWindows.of(Time.seconds(2))).sum(0);

        //打印结果
        sum.print().setParallelism(1);

        String jobName = StreamingDemoWithMyNoPralallelSourceBroadcast.class.getSimpleName();
        env.execute(jobName);
    }
}
