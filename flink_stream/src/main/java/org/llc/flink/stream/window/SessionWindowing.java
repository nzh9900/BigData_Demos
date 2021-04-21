package org.llc.flink.stream.window;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.ArrayList;
import java.util.List;


public class SessionWindowing {

    @SuppressWarnings("serial")
    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        final List<Tuple3<String, Long, Integer>> input = new ArrayList<>();

        input.add(new Tuple3<>("a", 1L, 1));
        input.add(new Tuple3<>("b", 1L, 1));
        input.add(new Tuple3<>("b", 3L, 1));
        input.add(new Tuple3<>("b", 5L, 1));
        input.add(new Tuple3<>("c", 6L, 1));
        // We expect to detect the session "a" earlier than this point (the old
        // functionality can only detect here when the next starts)
        input.add(new Tuple3<>("a", 10L, 1));
        // We expect to detect session "b" and "c" at this point as well
        input.add(new Tuple3<>("c", 11L, 1));

        DataStream<Tuple3<String, Long, Integer>> source =
                env.addSource(
                        new SourceFunction<>() {
                            private static final long serialVersionUID = 1L;

                            @Override
                            public void run(SourceContext<Tuple3<String, Long, Integer>> ctx) {
                                for (Tuple3<String, Long, Integer> value : input) {
                                    ctx.collectWithTimestamp(value, value.f1);
                                    ctx.emitWatermark(new Watermark(value.f1 - 1));
                                }
                                ctx.emitWatermark(new Watermark(Long.MAX_VALUE));
                            }

                            @Override
                            public void cancel() {
                            }
                        });

        // We create sessions for each id with max timeout of 3 time units
        DataStream<Tuple3<String, Long, Integer>> aggregated =
                source.keyBy(value -> value.f0)
                        .window(EventTimeSessionWindows.withGap(Time.milliseconds(3L)))
                        .sum(2);

        System.out.println("Printing result to stdout. Use --output to specify output path.");
        aggregated.print();

        env.execute();
    }
}
