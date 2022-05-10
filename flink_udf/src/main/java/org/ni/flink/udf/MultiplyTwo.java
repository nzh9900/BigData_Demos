package org.ni.flink.udf;

import org.apache.flink.table.functions.ScalarFunction;

public class MultiplyTwo extends ScalarFunction {

    public Integer eval(Integer num) {

        return num * 2;
    }
}
