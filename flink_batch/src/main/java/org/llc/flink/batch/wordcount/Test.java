package org.llc.flink.batch.wordcount;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.List;


public class Test {
    static final String bj = "每股收益";
    static final String bj2 = "报告日期";
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String path = WordCount.class.getClassLoader().getResource("601398.csv").getPath();

        CsvReader csvReader = env.readCsvFile(path);
        csvReader.setCharset("GBK");


        List ds0 = csvReader.includeFields("011100")     //����csv�ļ��е��ֶ��Ƿ��ȡ1�����ȡ,0������ȡ
                .includeFields("11100")
//                .ignoreFirstLine()          //�Ƿ���ӵ�һ��
                .ignoreInvalidLines()       //���ڲ��Ϸ������Ƿ����
                .ignoreComments("##")       //���Ӵ���##�ŵ���
                .lineDelimiter("\n")        //�зָ���
                .fieldDelimiter(",")        //�зָ���
                .types(String.class,String.class,String.class)
                .filter(d->{
                    System.out.println(d.f0);
                    String str = d.f0;
                    System.out.println(str.contains(bj));
                    return str.contains(bj) || str.contains(bj2);
                }).collect();

        DataSource<Tuple3<String, String, String>> ds = env.readCsvFile(path)
                .includeFields("011100")
                .ignoreFirstLine()
                .ignoreInvalidLines()
                .ignoreComments("##")
                .lineDelimiter("\n")
                .fieldDelimiter(",")
                .types(String.class,String.class,String.class);


        ds.print();
    }



}
