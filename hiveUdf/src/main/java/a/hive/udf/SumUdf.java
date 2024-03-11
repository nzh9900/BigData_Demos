package a.hive.udf;


import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

public class SumUdf extends UDF {
    public IntWritable evaluate(int a, int b) {
        return new IntWritable(a + b);
    }
}