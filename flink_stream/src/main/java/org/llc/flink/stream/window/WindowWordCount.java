package org.llc.flink.stream.window;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.llc.flink.stream.wordcount.WordCount;
import org.llc.flink.stream.wordcount.WordCountData;

public class WindowWordCount {


    public static void main(String[] args) throws Exception {

        // set up the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> text = env.fromElements(WordCountData.WORDS);

        final int windowSize = 10;
        final int slideSize = 5;

        DataStream<Tuple2<String, Integer>> counts =
                // split up the lines in pairs (2-tuples) containing: (word,1)
                text.flatMap(new WordCount.Tokenizer())
                        // create windows of windowSize records slided every slideSize records
                        .keyBy(value -> value.f0)
                        .countWindow(windowSize, slideSize)
                        // group by the tuple field "0" and sum up tuple field "1"
                        .sum(1);

            counts.print();

        // execute program
        env.execute("WindowWordCount");
    }
}
