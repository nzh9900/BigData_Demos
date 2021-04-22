package org.llc.flink.stream.source;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.llc.flink.stream.source.custom.CustomRichParallelSource;

/**
 * 使用多并行度的source
 *
 */
public class StreamingDemoWithMyRichPralalleSource {

    public static void main(String[] args) throws Exception {
        //获取Flink的运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //获取数据源
        DataStreamSource<Long> text = env.addSource(new CustomRichParallelSource()).setParallelism(2);

        DataStream<Long> num = text.map((MapFunction<Long, Long>) value -> {
            System.out.println("接收到数据：" + value);
            return value;
        });

        //每2秒钟处理一次数据
        //TumblingEventTimeWindows 滚动窗口
        DataStream<Long> sum = num.windowAll(TumblingEventTimeWindows.of(Time.seconds(2))).sum(0);

        //打印结果
        sum.print().setParallelism(1);

        String jobName = StreamingDemoWithMyRichPralalleSource.class.getSimpleName();
        env.execute(jobName);
    }
}
