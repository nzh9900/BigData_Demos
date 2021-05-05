package org.llc.flink.batch.wordcount;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.tuple.Tuple5;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.List;

//https://www.cnblogs.com/tree1123/p/12155955.html  DataSet常用API
public class Test {
    static final String bj = "每股收益";
    static final String bj2 = "报告日期";

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String path = WordCount.class.getClassLoader().getResource("601398.csv").getPath();

        CsvReader csvReader = env.readCsvFile(path);
        csvReader.setCharset("GBK");


        List<Tuple5<String, String, String, String, String>> ds0 = csvReader    //����csv�ļ��е��ֶ��Ƿ��ȡ1�����ȡ,0������ȡ
                .includeFields("11111")
//                .ignoreFirstLine()
                .ignoreInvalidLines()
                .ignoreComments("##")
                .lineDelimiter("\n")
                .fieldDelimiter(",")
                .types(String.class, String.class, String.class, String.class, String.class)
                .filter(d -> d.f0.contains(bj) || d.f0.contains(bj2)).collect();

//        DataSource<Tuple5<String, String, String, String, String>> ds = env.readCsvFile(path)
//                .includeFields("011100")
//                .ignoreFirstLine()
//                .ignoreInvalidLines()
//                .ignoreComments("##")
//                .lineDelimiter("\n")
//                .fieldDelimiter(",")
//                .types(String.class, String.class, String.class, String.class, String.class);

        final StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple5<String, String, String,  Double, String>> dataStream = senv.addSource(new SourceFunction<>() {

            @Override
            public void run(SourceContext<Tuple5<String, String, String, Double, String>> sourceContext) {
                Tuple5 tuple1 = new Tuple5("601398" + ds0.get(0).f1, "", "", Double.valueOf(ds0.get(1).f1), ds0.get(0).f1);
                Tuple5 tuple2 = new Tuple5("601398" + ds0.get(0).f2, "", "", Double.valueOf(ds0.get(1).f2), ds0.get(0).f2);
                Tuple5 tuple3 = new Tuple5("601398" + ds0.get(0).f3, "", "", Double.valueOf(ds0.get(1).f3), ds0.get(0).f3);
                Tuple5 tuple4 = new Tuple5("601398" + ds0.get(0).f4, "", "", Double.valueOf(ds0.get(1).f4), ds0.get(0).f4);
                sourceContext.collect(tuple1);
                sourceContext.collect(tuple2);
                sourceContext.collect(tuple3);
                sourceContext.collect(tuple4);
            }

            @Override
            public void cancel() {

            }
        });

        dataStream.addSink(new Flink2JdbcSink());

        senv.execute("save to mysql");

//        ds.print();
    }


}
