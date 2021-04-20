package org.llc.flink.stream;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

public class Test {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        String path = Test.class.getClassLoader().getResource("601398.csv").getPath();

        env.readCsvFile(path)
                .includeFields("11")
                .ignoreFirstLine().types(Tuple2.class);

        System.out.println("hello flink");
    }
}
