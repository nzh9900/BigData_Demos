package org.llc.flink.batch.wordcount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;


public class WordCountPojo {


    public static class Word {

        // fields
        private String word;
        private int frequency;

        // constructors
        public Word() {
        }

        public Word(String word, int i) {
            this.word = word;
            this.frequency = i;
        }

        // getters setters
        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "Word=" + word + " freq=" + frequency;
        }
    }

    public static void main(String[] args) throws Exception {


        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String path = WordCountPojo.class.getClassLoader().getResource("test.txt").getPath();

        DataSet<String> text = env.readTextFile(path);

        DataSet<Word> counts =
                text.flatMap(new Tokenizer())
                        .groupBy("word")
                        .reduce(
                                (ReduceFunction<Word>) (value1, value2) -> new Word(
                                        value1.word, value1.frequency + value2.frequency));
        counts.print();
    }


    public static final class Tokenizer implements FlatMapFunction<String, Word> {

        @Override
        public void flatMap(String value, Collector<Word> out) {

            String[] tokens = value.toLowerCase().split("\\W+");

            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Word(token, 1));
                }
            }
        }
    }
}
