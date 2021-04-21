package org.llc.flink.batch.wordcount;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.List;


public class Test {
    static final String bj = "每股收益";
    static final String bj2 = "报告日期";
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String path = WordCount.class.getClassLoader().getResource("601398.csv").getPath();

        CsvReader csvReader = env.readCsvFile(path);
        csvReader.setCharset("GBK");


        List<Tuple3<String, String, String>> ds0 = csvReader.includeFields("011100")     //����csv�ļ��е��ֶ��Ƿ��ȡ1�����ȡ,0������ȡ
                .includeFields("111111")
//                .ignoreFirstLine()
                .ignoreInvalidLines()
                .ignoreComments("##")
                .lineDelimiter("\n")
                .fieldDelimiter(",")
                .types(String.class,String.class,String.class)
                .filter(d-> d.f0.contains(bj) || d.f0.contains(bj2)).collect();

        DataSource<Tuple3<String, String, String>> ds = env.readCsvFile(path)
                .includeFields("011100")
                .ignoreFirstLine()
                .ignoreInvalidLines()
                .ignoreComments("##")
                .lineDelimiter("\n")
                .fieldDelimiter(",")
                .types(String.class,String.class,String.class);

        final StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple3<String, String, String>> dataStream = senv.addSource(new SourceFunction<>() {

            @Override
            public void run(SourceContext<Tuple3<String, String, String>> sourceContext) throws Exception {
                Tuple3 tuple3 = new Tuple3(ds0.get(0).f1, ds0.get(1).f1, null);
                sourceContext.collect(tuple3);
            }

            @Override
            public void cancel() {

            }
        });

        dataStream.addSink(new Flink2JdbcWriter());

        senv.execute("save to mysql");

        ds.print();
    }



}
